package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JSONReaderUtil;
import com.database.dao.CreateJobPayloadDataDao;
import com.database.dao.MapJobProblemDao;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	private static final Logger LOGGER = LogManager.getLogger(DataProviderUtils.class);

    @DataProvider(name = "LoginAPIDataProvider", parallel = true)
    public static Iterator<UserBean> loginAPIDataProvider() {
    	LOGGER.info("Loading Data from the CSV file testData/LoginCreds.csv ");
        return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);  // Pass UserBean.class
    } 
    
    @DataProvider(name = "CreateJobAPIDataProvider", parallel= true)
    public static Iterator<CreateJobPayload>  createJobDataProvider() {
    	LOGGER.info("Loading Data from the CSV file testData/CreateJobData.csv");
    	Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv",
    			CreateJobBean.class);
 
    	List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
    	CreateJobBean tempBean;
    	CreateJobPayload tempPayload;
    	while(createJobBeanIterator.hasNext()) {
    	tempBean= createJobBeanIterator.next();
    	tempPayload = CreateJobBeanMapper.mapper(tempBean);   
    	payloadList.add(tempPayload);
    	}
    		
    	return payloadList.iterator();
    }
    
    @DataProvider(name = "CreateJobAPIFakerDataProvider", parallel= true)
    public static Iterator<CreateJobPayload>  createJobFakeDataProvider() {
    	String fakerCount = System.getProperty("fakerCount", "5");
    	int fakerCountInt = Integer.parseInt(fakerCount);
    	LOGGER.info("Generating fake Create job sata with the faker count {}", fakerCount);
    	Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
        
    }
    
    @DataProvider(name = "LoginAPIJSONDataProvider", parallel = true)
    public static Iterator<UserBean> LoginAPIJSONDataProvider(){
    	LOGGER.info("Loading Data from the JSON file testData/LoginAPITestData.json");
		return JSONReaderUtil.LoadJSON("testData/LoginAPITestData.json", UserBean[].class);
    	
    }
    
    @DataProvider(name = "CreateJobAPIJSONDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> CreateJobAPIJSONDataProvider(){
    	LOGGER.info("Loading Data from the JSON file testData/CreateJobAPIData.json");
		return JSONReaderUtil.LoadJSON("testData/CreateJobAPIData.json", CreateJobPayload[].class);
    	
    }
    
    
    @DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
    public static Iterator<UserBean> LoginAPIExcelDataProvider() {
        LOGGER.info("Loading Data from the Excel file testData/PhoenixTestData.xlsx and sheet is LoginTestData");
        return ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx", "LoginTestData", UserBean.class);
    }

    
    @DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> CreateJobAPIExcelDataProvider(){
    	LOGGER.info("Loading Data from the Excel file testData/PhoenixTestData.xlsx and sheet is CreateJobTestData");
    	Iterator<CreateJobBean> iterator = ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx", "CreateJobTestData", CreateJobBean.class);
    	List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
    	CreateJobBean tempBean;
    	CreateJobPayload tempPayload;
    	while(iterator.hasNext()) {
    	tempBean= iterator.next();
    	tempPayload = CreateJobBeanMapper.mapper(tempBean);   
    	payloadList.add(tempPayload);
    	}
    		
    	return payloadList.iterator();
    } 	
   
    @DataProvider(name = "CreateJobAPIDBDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> CreateJobAPIDBDataProvider() {
    	LOGGER.info("Loading Data from the Database for CreateJobPayload");
		List<CreateJobBean> beanList=CreateJobPayloadDataDao.getCreateJobPayLoadData();
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (CreateJobBean createJobBean: beanList) {
			CreateJobPayload payload = CreateJobBeanMapper.mapper(createJobBean);
			payloadList.add(payload);
		}
		return payloadList.iterator();
		
	} 
    
}
