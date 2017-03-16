package com.weixin.sdk.utils;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class JaxbKit {
	
	protected final static Logger LOG = Logger.getLogger(JaxbKit.class);

	/**
	 * string object
	 *
	 * @param src
	 *            src
	 * @param clazz
	 *            clazz
	 * @param <T>
	 *            t
	 * @return T t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(String src, Class<T> clazz) {
		T result = null;
		try {
			Unmarshaller avm = JAXBContext.newInstance(clazz).createUnmarshaller();
			result = (T) avm.unmarshal(new StringReader(src));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(File xmlFile, Class<T> clazz) {
		T result = null;
		try {
			Unmarshaller avm = JAXBContext.newInstance(clazz).createUnmarshaller();
			result = (T) avm.unmarshal(xmlFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(InputStream xmlStream, Class<T> clazz) {
		T result = null;
		try {
			Unmarshaller avm = JAXBContext.newInstance(clazz).createUnmarshaller();
			result = (T) avm.unmarshal(xmlStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * object string
	 * 
	 * @param jaxbElement
	 *            jaxb
	 * @return string
	 * @author kid create 2013-4-1
	 */
	public static String marshal(Object jaxbElement) {
		StringWriter sw = null;
		try {
			Marshaller fm = JAXBContext.newInstance(jaxbElement.getClass()).createMarshaller();
			fm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			sw = new StringWriter();
			fm.marshal(jaxbElement, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}
}
