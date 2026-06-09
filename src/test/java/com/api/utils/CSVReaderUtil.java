package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {
	
	private CSVReaderUtil() {
		
	}

// Job: Help me read the CSV file and Map it a Bean
	
public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean) {
		
		InputStream is=Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);  // CSVReader Constructor
		
		
		//Write the code to Map the CSV to POJO 
		
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(bean)
				.withIgnoreEmptyLine(true)
				.build();
				
		
		List<T> userList=csvToBean.parse();
		return userList.iterator();
		
	}

}
