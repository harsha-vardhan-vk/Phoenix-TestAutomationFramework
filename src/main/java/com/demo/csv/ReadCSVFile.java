package com.demo.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) throws IOException, CsvException {
		// Code to read the CSV file in Java!! [Important interview question]
//		
//		
//		File csvFile = new File(
//				"C:\\Projects\\Tech with Jatin\\Java Module\\PhoenixTestAutomationFramework\\src\\main\\resources\\testData\\LoginCreds.csv");
//		FileReader fr = new FileReader(csvFile);
//		
//		
		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);  // CSVReader Constructor
		List<String[]> dataList = csvReader.readAll();

		for (String[] dataArray : dataList) {
			for (String data : dataArray) {
				System.out.print(data + " ");
			}
			System.out.println(" ");
		}

	}

}
