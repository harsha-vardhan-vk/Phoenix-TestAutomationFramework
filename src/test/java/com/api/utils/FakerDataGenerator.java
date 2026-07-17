package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {

    // Util Class
    private static final Faker faker = new Faker(new Locale("en", "IN")); // fixed locale
    private static final String COUNTRY = "India";
    private static final Random RANDOM = new Random();

    private static final int MST_SERVICELOCATION_ID = 0;
    private static final int MST_PLATFORM_ID = 2;
    private static final int MST_WARRANTY_STATUS_ID = 1;
    private static final int MST_OEM_ID = 1;
    private static final int MST_PRODUCT_ID = 1;
    private static final int MST_MODEL_ID = 1;

    private static final int[] validProblemId = {
        1,2,3,4,5,6,7,8,9,10,11,12,15,17,19,20,22,24,26,27,28,29
    };
    
    private static final Logger LOGGER = LogManager.getLogger(FakerDataGenerator.class);

    private FakerDataGenerator() {
    	
    }
    public static CreateJobPayload generateFakeCreateJobData() {
    
    LOGGER.info("Generating the fake payload for Create job");
    Customer customer = generateFakeCustomerData();
    CustomerAddress customerAddress = generateFakeCustomerAddressData();
    CustomerProduct customerProduct = generateFakeCustomerProduct();
    List<Problems> problemsList = generateFakeProblemsList();
    CreateJobPayload payload = new CreateJobPayload(MST_SERVICELOCATION_ID, MST_PLATFORM_ID,
    MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
    return payload;
    }

    public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
        LOGGER.info("Generating the fake {} payload for Create job", count);
    	List<CreateJobPayload> payloadList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Customer customer = generateFakeCustomerData();
            CustomerAddress customerAddress = generateFakeCustomerAddressData();
            CustomerProduct customerProduct = generateFakeCustomerProduct();
            List<Problems> problemsList = generateFakeProblemsList();

            CreateJobPayload payload = new CreateJobPayload(
                MST_SERVICELOCATION_ID,
                MST_PLATFORM_ID,
                MST_WARRANTY_STATUS_ID,
                MST_OEM_ID,
                customer,
                customerAddress,
                customerProduct,
                problemsList
            );
            payloadList.add(payload);
        }
        return payloadList.iterator();
    }

    private static Customer generateFakeCustomerData() {
        return new Customer(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.numerify("812#######"),
            faker.numerify("70########"),
            faker.internet().emailAddress(),
            faker.internet().emailAddress()
        );
    }

    private static CustomerAddress generateFakeCustomerAddressData() {
        return new CustomerAddress(
            faker.numerify("###"),
            faker.address().streetName(),
            faker.address().streetName(),
            faker.address().streetName(),
            faker.address().streetName(),
            faker.numerify("######"),
            COUNTRY,
            faker.address().state()
        );
    }

    private static CustomerProduct generateFakeCustomerProduct() {
        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imei = faker.numerify("###############"); // 15 digits
        String popUrl = faker.internet().url();

        return new CustomerProduct(
            dop,
            imei,
            imei,
            imei,
            popUrl,
            MST_PRODUCT_ID,
            MST_MODEL_ID
        );
    }

    private static List<Problems> generateFakeProblemsList() {
        int count = RANDOM.nextInt(3) + 1;
        List<Problems> problemList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int randomIndex = RANDOM.nextInt(validProblemId.length);
            String fakeRemark = faker.lorem().sentence(5);
            problemList.add(new Problems(validProblemId[randomIndex], fakeRemark));
        }
        return problemList;
    }
}
