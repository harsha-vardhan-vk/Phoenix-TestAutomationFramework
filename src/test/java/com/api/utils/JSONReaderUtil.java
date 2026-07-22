package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.UserCredentials;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qameta.allure.Step;

public class JSONReaderUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(JSONReaderUtil.class);

	@Step("Loading test data from the JSON file")
    public static <T> Iterator<T> LoadJSON(String fileName, Class<T[]> clazz)  {
        
    	LOGGER.info("Reading the JSON from the file {}", fileName);
    	InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    	ObjectMapper objectMapper = new ObjectMapper();
    	T[] classArray;
    	List<T> list = null;
		
    	try {
    		LOGGER.info("Converting the JSON Data to the bean class {}", fileName);
			classArray = objectMapper.readValue(is, clazz);
			list=Arrays.asList(classArray);	
		} catch (IOException e) {
			LOGGER.error("Cannot read the json from the file {}", fileName, e);
			e.printStackTrace();
		}
    	
    	
    	return list.iterator();
    }
}