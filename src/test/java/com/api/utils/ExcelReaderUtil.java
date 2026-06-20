package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil {

    private ExcelReaderUtil() {
        
    }
    
    public static <T> Iterator<T> loadTestData(String xlsxFile, String sheetname, Class<T> clazz) {
        // APACHE POI OOXML LIBRARY
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(xlsxFile);
        XSSFWorkbook myWorkBook = null;
        try {
            myWorkBook = new XSSFWorkbook(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Focus on the Sheet
        XSSFSheet mySheet = myWorkBook.getSheet(sheetname);
        
        List<T> List = Poiji.fromExcel(mySheet, clazz);
        
        return List.iterator();
    }
}