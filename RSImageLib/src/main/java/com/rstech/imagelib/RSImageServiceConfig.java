package com.rstech.imagelib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;  

public class RSImageServiceConfig extends Properties {
	private static final long serialVersionUID = 5564122206839060047L;
	private static final Class thisClass = RSImageServiceConfig.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	public static final String DEFAULT_IMAGE_PNG = "DefaultImage.png";
	public static final String DEFAULT_IMAGE_JPG = "DefaultImage.jpg";

	public final static String XML_CONF_FILE_NAME = "RSImageService.cfg.xml";
	public final static String DEFAULT_DB_MODE = "dev";
	public static final int DEFAULT_SCALE_MAX_HEIGHT = 300;
	public static final int DEFAULT_SCALE_MAX_WIDTH = 300;

	private static final RSImageServiceConfig instance;

	 

	public final static boolean IS_TEST_ENABLED;
	public final static String TEST_ROOT;
	public final static String TEST_SUFFIX;
	public final static String[] TEST_RECORDS; 
	public static final int DEFAULT_SCALE_WIDTH;
	public static final int DEFAULT_SCALE_HEIGHT;

	public static final boolean DEFAULT_IS_IMAGE_CROPPED;
	public static final String DEFAULT_IMAGE_NAME_JPG;
	public static final String DEFAULT_IMAGE_NAME_PNG;
	public static final String OUTPUT_TYPE_JPG = "jpg";
	public static final String OUTPUT_TYPE_JPG_MIME = "image/jpeg";

	public static final String OUTPUT_TYPE_PNG = "png";
	public static final String OUTPUT_TYPE_PNG_MIME = "image/png";
	public static final String OUTPUT_TYPE_DEFAULT;

	static {
		System.out.println("Entering static Initalization!");
		String methodName = "Static Initalization block";
		instance = new RSImageServiceConfig();
		instance.loadConfData();
		  
		DEFAULT_SCALE_WIDTH = getPropertyInt("global.scale.height",
				DEFAULT_SCALE_MAX_WIDTH);
		DEFAULT_SCALE_HEIGHT = getPropertyInt("global.scale.width",
				DEFAULT_SCALE_MAX_HEIGHT);

		DEFAULT_IS_IMAGE_CROPPED = getPropertyIsTrue("global.cropped.defaultIsImageCropped");
		OUTPUT_TYPE_DEFAULT = instance.getProperty("global.outputType",
				OUTPUT_TYPE_JPG);
		DEFAULT_IMAGE_NAME_JPG = instance.getProperty(
				"global.defaultImageName.jpg", DEFAULT_IMAGE_JPG);
		DEFAULT_IMAGE_NAME_PNG = instance.getProperty(
				"global.defaultImageName.png", DEFAULT_IMAGE_PNG);

		String tmpTestIsEnabled = instance.getProperty("test.isEnabled");
		if (tmpTestIsEnabled != null
				&& "true".equals(tmpTestIsEnabled.trim().toLowerCase())) {
			IS_TEST_ENABLED = true;
			TEST_ROOT = instance.getProperty("test.root");
			TEST_SUFFIX = instance.getProperty("test.suffix");
			String data = instance.getProperty("test.records");
			if (data != null && false == "".equals(data.trim())) {
				ArrayList<String> list = new ArrayList<String>();
				String[] splitList = data.split(", ");

				for (String rec : splitList) {
					System.out.println("note: " + rec);
					String cleanRec = rec.trim();

					if (cleanRec != null && false == "".equals(cleanRec)) {
						list.add(cleanRec);
					}
				}
				TEST_RECORDS = list.toArray(new String[0]);
			} else {
				TEST_RECORDS = null;
			}
		} else {
			IS_TEST_ENABLED = false;
			TEST_ROOT = null;
			TEST_SUFFIX = null;
			TEST_RECORDS = null;
		}

		System.out.println("Configuration Settings: \n" + instance + "\n");
		logger.info(methodName + "Configuations settings: \n" + instance + "\n");
	}

	private RSImageServiceConfig() {
		super();
	}

	private static int getPropertyInt(String string, int defaultValue) {
		String methodName = "getPropertyInt";
		int result = defaultValue;
		String tmpStr = instance.getProperty(string);
		if (tmpStr != null && false == "".equals(tmpStr.trim())) {
			try {
				result = Integer.parseInt(tmpStr);
			} catch (NumberFormatException e) {
				logger.error(methodName + "Bad value for property " + string, e);

			}
		}
		return result;
	}

	private static boolean getPropertyIsTrue(String string) {
		boolean result = false;
		String tmpStr = instance.getProperty(string);
		if (tmpStr != null && "true".equals(tmpStr.trim())) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public void loadConfData() {
		String methodName = "loadConfData";
		File tmpFile = new File(XML_CONF_FILE_NAME);
		InputStream inStream = null;
		boolean isLocal = false;
		if (tmpFile != null && tmpFile.exists()) {
			try {
				inStream = new FileInputStream(tmpFile);
			} catch (FileNotFoundException e) {
				logger.warn(methodName + "Failed to open local conf file ["
						+ XML_CONF_FILE_NAME + "] :" + e);
				e.printStackTrace();
			}
		}

		if (inStream == null) {
			logger.info(methodName + "No local conf file ["
					+ XML_CONF_FILE_NAME + "] availible, using package conf.");

			inStream = thisClass.getResourceAsStream(XML_CONF_FILE_NAME);
			isLocal = true;
		}
		try {
			this.loadConfData(instance, inStream);
		} catch (InvalidPropertiesFormatException e) {
			logger.warn(methodName + "Invalid "
					+ (isLocal ? "jar based" : "local file")
					+ " properties xml conf file [" + XML_CONF_FILE_NAME
					+ "] :" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.warn(methodName + "Failed to read "
					+ (isLocal ? "jar based" : "local file")
					+ " properties xml conf file [" + XML_CONF_FILE_NAME
					+ "] :" + e);
			e.printStackTrace();
		} catch (ExceptionInInitializerError e) {
			logger.warn(methodName + "Failed to read "
					+ (isLocal ? "jar based" : "local file")
					+ " properties xml conf file [" + XML_CONF_FILE_NAME
					+ "] :" + e);
			e.printStackTrace();
		}
	}

	public void loadConfData(RSImageServiceConfig instance, InputStream stream)
			throws InvalidPropertiesFormatException, IOException { 
		 
	}

	public static RSImageServiceConfig getInstance() {
		return instance;
	}

	/**
	 * @param args
	 */
	public static final void main(String[] args) {
		System.out.println("Is test Enabled "
				+ RSImageServiceConfig.IS_TEST_ENABLED);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		  
		sb.append("DEFAULT_SCALE_WIDTH ").append(DEFAULT_SCALE_WIDTH)
				.append("\n");
		sb.append("DEFAULT_SCALE_HEIGHT ").append(DEFAULT_SCALE_HEIGHT)
				.append("\n");

		sb.append("DEFAULT_IS_IMAGE_CROPPED ").append(DEFAULT_IS_IMAGE_CROPPED)
				.append("\n");
		sb.append("DEFAULT_IMAGE_NAME_JPG ").append(DEFAULT_IMAGE_NAME_JPG)
				.append("\n");
		sb.append("DEFAULT_IMAGE_NAME_PNG ").append(DEFAULT_IMAGE_NAME_PNG)
				.append("\n");
		return sb.toString();
	}

	public static boolean isValidOutputType(String outputType) {
		String methodName = "isValidOutputType";
		boolean result = false;
		if (outputType != null || OUTPUT_TYPE_JPG.equals(outputType)
				|| OUTPUT_TYPE_PNG.equals(outputType)) {
			result = true;
		} else {
			logger.debug(methodName + "Invalid output type " + outputType);
		}
		return result;
	}

	public static String getMimeTypeForOutput(String outputType) {
		String methodName = "getMimeTypeForOutput";
		logger.debug(methodName + "Output Type = " + outputType);

		String mimeType = OUTPUT_TYPE_JPG_MIME;
		if (OUTPUT_TYPE_PNG.equals(outputType)) {
			mimeType = OUTPUT_TYPE_PNG_MIME;
		}
		logger.debug(methodName + "Output Mime Type = " + mimeType);
		return mimeType;
	}
}
