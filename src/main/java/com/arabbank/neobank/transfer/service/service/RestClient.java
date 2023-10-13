package com.arabbank.neobank.transfer.service.service;

import com.arabbank.neobank.transfer.service.config.ApplicationConfig;
import com.arabbank.neobank.transfer.service.model.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class RestClient {
    @Autowired
    private ApplicationConfig applicationConfig;
    OkHttpClient client = new OkHttpClient().newBuilder().build();


    public CustomerProfileResponseDTO getCustomerProfileByCustomerId(String customerId) throws IOException {

        Request request = new Request.Builder().url(applicationConfig.getCustomerUrl() + "/customer?customerId=" + customerId).method("GET", null).addHeader("accept", "*/*").build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            CustomerProfileResponseDTO profile = mapper.readValue(response.body().bytes(), CustomerProfileResponseDTO.class);
            return profile;
        }
        return null;
    }

    public AccountResponseDto getAccountByAccountNumber(String accountNumber) throws IOException {
        Request request = new Request.Builder().url(applicationConfig.getAccountUrl() + "/get-account?accountNumber=" + accountNumber).method("GET", null).addHeader("accept", "*/*").build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            AccountResponseDto accountDetails = mapper.readValue(response.body().bytes(), AccountResponseDto.class);
            return accountDetails;
        }
        response.close();
        return null;
    }

    public void sendTransferDetails(TransferFinalResponseDTO transferFinalResponseDTO) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(finalResponseToJson(transferFinalResponseDTO).getBytes(StandardCharsets.UTF_8));
        Request request = new Request.Builder().url(applicationConfig.getAccountUrl() + "/get-transaction").method("POST", body).addHeader("Content-Type", "application/json").build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            response.close();
        }
        //AccountResponseDto accountDetails = mapper.readValue(response.body().bytes(), AccountResponseDto.class);

    }

    public OfferResponseDto sendOfferDetails(OfferRequestDto offerRequestDto) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(offerResponseToJson(offerRequestDto).getBytes(StandardCharsets.UTF_8));
        Request request = new Request.Builder().url(applicationConfig.getOfferUrl() + "/offer-discount").method("POST", body).addHeader("Content-Type", "application/json").build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            OfferResponseDto offerResponseDto = mapper.readValue(response.body().bytes(), OfferResponseDto.class);
            return offerResponseDto;
        }
        response.close();
        return null;
    }

    public String finalResponseToJson(TransferFinalResponseDTO transferFinalResponseDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(transferFinalResponseDTO);
    }

    public String offerResponseToJson(OfferRequestDto offerRequestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(offerRequestDto);
    }


    //    @PostConstruct
    public void test() throws IOException {

    }
}
