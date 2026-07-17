package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil {
	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);
    private ExcelReaderUtil() {
        
    }
    
    public static <T> Iterator<T> loadTestData(String xlsxFile, String sheetname, Class<T> clazz) {
    	LOGGER.info("Reading the test data from .xlsx file {} and the sheet name is {}", xlsxFile, sheetname);
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(xlsxFile);
        XSSFWorkbook myWorkBook = null;
        try {
            myWorkBook = new XSSFWorkbook(is);
        } catch (IOException e) {
        	LOGGER.error("Cannot read the excel {}", xlsxFile, e);
            e.printStackTrace();
        }

        // Focus on the Sheet
        XSSFSheet mySheet = myWorkBook.getSheet(sheetname);
        LOGGER.info("Converting the XSSFSheet to POJO Class of type {}", sheetname,clazz);
        List<T> List = Poiji.fromExcel(mySheet, clazz);
        
        return List.iterator();
    }
}