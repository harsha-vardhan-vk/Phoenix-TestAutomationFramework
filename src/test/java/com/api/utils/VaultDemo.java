package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDemo {

    public static void main(String[] args) throws VaultException {
    	
    	String data = System.getenv("VAULT_SERVER");
    	String dataT = System.getenv("VAULT_TOKEN");
    	System.out.println(data);
    	System.out.println(dataT);
//    	VaultConfig vaultConfig = new VaultConfig()
//    			.address("http://13.63.140.157:8200/")
//    			.token("root")
//    			.build();
//    	
//    	Vault vault = new Vault(vaultConfig);
//    	
//    	LogicalResponse response = vault.logical().read("secret/phoenix/qa/database");
//    	
//    	Map<String, String> dataMap=response.getData();
//    	System.out.println("DB_URL = " + dataMap.get("DB_URL"));
//    	System.out.println("DB_USER_NAME = " + dataMap.get("DB_USER_NAME"));
//    	System.out.println("DB_USER_PASSWORD = " + dataMap.get("DB_USER_PASSWORD"));
    }
}
