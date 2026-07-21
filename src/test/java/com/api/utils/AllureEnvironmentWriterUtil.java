package com.api.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class AllureEnvironmentWriterUtil {
    private static final Logger LOGGER = Logger.getLogger(AllureEnvironmentWriterUtil.class.getName());

    public static void createEnvironmentPropertiesFile() {
        String folderPath = "target/allure-results";
        File file = new File(folderPath);
        file.mkdir();

        Properties prop = new Properties();
        prop.setProperty("Project Name", "PhoenixTestAutomationFramework");
        prop.setProperty("Env", ConfigManager.env);
        prop.setProperty("BASE_URI", ConfigManager.getProperty("BASE_URI"));

        prop.setProperty("OS Name", System.getProperty("os.name"));
        prop.setProperty("OS Version", System.getProperty("os.version"));
        prop.setProperty("Java Version", System.getProperty("java.version"));

        try (FileWriter fw = new FileWriter(folderPath + "/environment.properties")) {
            prop.store(fw, "Environment Properties");
            LOGGER.info("Created the environment.properties file at " + folderPath);
        } catch (IOException e) {
            LOGGER.severe("Unable to create environment.properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
