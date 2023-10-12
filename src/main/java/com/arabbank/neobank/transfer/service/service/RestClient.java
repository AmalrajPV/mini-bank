package com.arabbank.neobank.transfer.service.service;

import com.arabbank.neobank.transfer.service.model.dto.AccountResponseDto;
import com.arabbank.neobank.transfer.service.model.dto.CustomerProfileResponseDTO;
import com.arabbank.neobank.transfer.service.model.dto.TransferFinalResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
public class RestClient {
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    private final String CUSTOMER_PROFILE_BASEURL = "http://172.16.4.87:8080";
    private final String BANK_BASEURL = "http://172.16.4.98:8080";

    public CustomerProfileResponseDTO getCustomerProfileByCustomerId(String customerId) throws IOException {

        Request request = new Request.Builder()
                .url(CUSTOMER_PROFILE_BASEURL + "/customer?customerId=" + customerId)
                .method("GET", null)
                .addHeader("accept", "*/*")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            CustomerProfileResponseDTO profile = mapper.readValue(response.body().bytes(), CustomerProfileResponseDTO.class);
            return profile;
        }
        return null;
    }

    public AccountResponseDto getAccountByAccountNumber(String accountNumber) throws IOException {
        Request request = new Request.Builder()
                .url(BANK_BASEURL + "/get-account?accountNumber=" + accountNumber)
                .method("GET", null)
                .addHeader("accept", "*/*")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            AccountResponseDto accountDetails = mapper.readValue(response.body().bytes(), AccountResponseDto.class);
            return accountDetails;
        }
        return null;
    }

    public void sendTransferDetails(TransferFinalResponseDTO transferFinalResponseDTO) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(finalResponseToJson(transferFinalResponseDTO).getBytes(StandardCharsets.UTF_8));
        Request request = new Request.Builder()
                .url(BANK_BASEURL + "/account/getTransactionInfo")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        //AccountResponseDto accountDetails = mapper.readValue(response.body().bytes(), AccountResponseDto.class);

    }

    public String finalResponseToJson(TransferFinalResponseDTO transferFinalResponseDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(transferFinalResponseDTO);
    }


    @PostConstruct
    public void test() throws IOException {
//        getAccountByAccountNumber("123456");
        TransferFinalResponseDTO transferFinalResponseDTO = new TransferFinalResponseDTO();
        transferFinalResponseDTO.setTransferID("jgg");
        transferFinalResponseDTO.setAmount(BigDecimal.valueOf(1222));
        transferFinalResponseDTO.setReceiverAccountNumber("1233456");
        transferFinalResponseDTO.setSenderAccountNumber("564798");
        sendTransferDetails(transferFinalResponseDTO);

    }
}
