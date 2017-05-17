package com.wdz.io.conf.reader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesReader {

	private Logger logger = Logger.getLogger(PropertiesReader.class);

	public Properties getProperties(String pathToConfigFile) {
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(pathToConfigFile)) {
			logger.info("load configuration data " + pathToConfigFile);
			properties.load(inputStream);
			return properties;
		} catch (Throwable throwable) {
			logger.error("error on loading properties configuration", throwable);
			throwable.printStackTrace();
		}
		return null;
	}
}