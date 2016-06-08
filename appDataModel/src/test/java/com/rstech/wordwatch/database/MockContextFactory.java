package com.rstech.wordwatch.database;

import java.util.*;

import javax.naming.*;
import javax.naming.spi.InitialContextFactory;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;

@SuppressWarnings("unchecked")
public class MockContextFactory implements InitialContextFactory {
	private static final Class thisClass = MockContextFactory.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	private static Map savedSystemProps = new HashMap();

	private static Hashtable delegateEnv;

	private static Context delegateContext;

	private static Context rootContext;

	/**
	 * Singleton for initial context. Instantiates and returns root/initial
	 * <code>MockContext</code> object that will be used as starting point for
	 * all naming operations. <code>MockContext</code> is then used by
	 * <code>javax.naming.InitialContext</code> object. It also creates the
	 * delegate context if the delegate environment is set. MockContextFactory
	 * caches the delegate context once it's created.
	 * 
	 * @see javax.naming.spi.InitialContextFactory#getInitialContext(java.util.Hashtable)
	 * @return <code>MockContext</code> object
	 */
	public Context getInitialContext(Hashtable environment)
			throws NamingException {
		String methodName = "getInitialContext";
		logger.debug("entering " + methodName);
		
		if (delegateContext == null && delegateEnv != null) {
			delegateContext = new InitialContext(delegateEnv);
		}

		if (rootContext == null) {
			rootContext = delegateContext;
		}
		logger.debug("exiting " + methodName);

		return rootContext;
	}

	/**
	 * Sets the environment of the delegate JNDI context. Normally, this is the
	 * environment of the application server. At the very minimum, the
	 * environment includes PROVIDER_URL and INITIAL_CONTEXT_FACTORY.
	 * <code>MockContext</code> first tries to look up the object in its local
	 * tree. If the object is not found, it will look in the delegate context.
	 * 
	 * @param env
	 *            JNDI properties of the delegate environment
	 */
	public static void setDelegateEnvironment(Hashtable env) {
		delegateEnv = env;

	}

	/**
	 * Sets the delegate context. Normally, this is the initial context of the
	 * application server.
	 * 
	 * <code>MockContext</code> first tries to look up the object in its local
	 * tree. If the object is not found, it will look in the delegate context.
	 * 
	 * Example: <code>
	 * MockContextFactory.setDelegateContext( new InitialContext() );
	 * </code>
	 * 
	 * @param ctx
	 *            delegate context
	 */
	public static void setDelegateContext(Context ctx) {
		delegateContext = ctx;
	}

	/**
	 * Sets the <code>MockContextFactory</code> as the initial context factory.
	 * This helper method sets the <code>Context.INITIAL_CONTEXT_FACTORY</code>
	 * and <code>Context.URL_PKG_PREFIXES</code> system properties. The second
	 * one is needed to be able to handle java:comp context correctly. The
	 * method also saves the current values of these properties so they can be
	 * restored later on using <code>revertSetAsInitial</code>. This method is
	 * normally called from <code>setUp</code>
	 * <p>
	 * You can also set these properties directly:
	 * 
	 * <pre>
	 * <code>
	     *  java.naming.factory.initial=org.mockejb.jndi.MockContextFactory
	     *  java.naming.factory.url.pkgs=org.mockejb.jndi
	     * </code>
	 * 
	 * <pre>
	 * 
	 * 
	 * @throws NamingException
	 */
	public static void setAsInitial() throws NamingException {
		// Preserve current set system props

		String key = Context.INITIAL_CONTEXT_FACTORY;
		savedSystemProps.put(key, System.getProperty(key));

		key = Context.URL_PKG_PREFIXES;
		savedSystemProps.put(key, System.getProperty(key));

		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				MockContextFactory.class.getName());
		System.setProperty(Context.URL_PKG_PREFIXES, "com.dhgate.mockejb3.jndi");
	}

	/**
	 * Restores the properties changed by <code>setAsInitial()</code> This
	 * method should be called in <code>tearDown()</code> to clean up all
	 * changes to the environment in case if the test is running in the app
	 * server.
	 * <p>
	 * This method also cleans the initial context.
	 */
	public static void revertSetAsInitial() {
		String methodName = "revertSetAsInitial";
		logger.debug("entering " + methodName);
		
		Iterator i = savedSystemProps.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry) i.next();
			restoreSystemProperty((String) entry.getKey(),
					(String) entry.getValue());
		}
		logger.debug("exiting " + methodName);

		rootContext = null;

	}

	private static void restoreSystemProperty(String key, String value) {
		String methodName = "restoreSystemProperty";
		logger.debug("entering " + methodName);
		

		if (value != null)
			System.setProperty(key, value);
		else
			System.getProperties().remove(key);
		logger.debug("exiting " + methodName);

	}
}
