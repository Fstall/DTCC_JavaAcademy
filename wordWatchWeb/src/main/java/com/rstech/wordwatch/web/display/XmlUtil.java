package com.rstech.wordwatch.web.display;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtil {

	public static String generateXml(Object object) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		try {
			prepareMarshaller().marshal(object, os);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		try {
			return os.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static Marshaller prepareMarshaller() throws JAXBException {
		JAXBContext context = prepareContext();
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		return marshaller;
	}

	private static JAXBContext prepareContext() throws JAXBException {
		return JAXBContext.newInstance(WordOfTheDay.class);
	}

	public static Object parseXml(String xml) {
		Object result = null;

		try {
			result = prepareUnmarshaller().unmarshal(
					new ByteArrayInputStream(xml.getBytes()));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return result;
	}

	private static Unmarshaller prepareUnmarshaller() throws JAXBException {
		JAXBContext context = prepareContext();

		return context.createUnmarshaller();
	}
}