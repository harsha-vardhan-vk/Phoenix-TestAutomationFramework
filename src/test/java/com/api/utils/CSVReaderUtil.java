package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class CSVReaderUtil {
	
	private CSVReaderUtil() {
		
	}

// Job: Help me read the CSV file and Map it a Bean
	
public static Iterator<UserBean> loadCSV(String pathOfCSVFile) {
		
		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);  // CSVReader Constructor
		
		
		//Write the code to Map the CSV to POJO 
		
		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(UserBean.class)
				.withIgnoreEmptyLine(true)
				.build();
				
		
		List<UserBean> userList=csvToBean.parse();
		return userList.iterator();
		
	}

}
