package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOld {

    private static Properties prop = new Properties();

    private ConfigManagerOld() {
    	//private constructor
    }
    static {
    	//Operation of loading the properties file in the memory 
    	// static block it will executed! Once During_class Loading Time!
        File configFile = new File(
            System.getProperty("user.dir") + File.separator + "src" 
        + File.separator + "test"
        + File.separator +"resources"
        + File.separator +"config"
        + File.separator +"config.properties"
        );
        FileReader fileReader = null;
        try {
        	fileReader = new FileReader(configFile);
        	prop.load(fileReader);
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}


