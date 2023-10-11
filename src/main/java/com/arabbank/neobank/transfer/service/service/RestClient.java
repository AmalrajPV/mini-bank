package com.arabbank.neobank.transfer.service.service;

import com.arabbank.neobank.transfer.service.model.dto.CustomerProfileResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RestClient {
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    private final String BASEURL = "http://172.16.4.87:8080";

    public CustomerProfileResponseDTO getCustomerProfileByCustomerId(String customerId) throws IOException {

        Request request = new Request.Builder()
                .url(BASEURL + "/customer?customerId="+customerId)
                .method("GET", null)
                .addHeader("accept", "*/*")
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            ObjectMapper mapper=new ObjectMapper();
            CustomerProfileResponseDTO profile = mapper.readValue(response.body().bytes(), CustomerProfileResponseDTO.class);
           return profile;
        }
        return null;
    }
//    @PostConstruct
    public void test() throws IOException {
        getCustomerProfileByCustomerId("1001");
    }
}
