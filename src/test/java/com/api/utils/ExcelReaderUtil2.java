package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil2 {

	private ExcelReaderUtil2() {
		
	}
	public static <T> Iterator<T> loadTestData(String sheetname, Class<T> clazz)  {
	//APACHE POI OOXML LIBRARY
	InputStream is = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("testData/PhoenixTestData.xlsx");	
	XSSFWorkbook myWorkBook = null;
	try {
		myWorkBook = new XSSFWorkbook(is);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	
	//Focus on the Sheet
	XSSFSheet mySheet = myWorkBook.getSheet("LoginTestData");
	
	List<T> dataList=Poiji.fromExcel(mySheet, clazz);
	
	return dataList.iterator();

	
	}

	}
