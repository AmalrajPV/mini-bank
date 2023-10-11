package com.arabbank.neobank.transfer.service.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerProfileResponseDTO {
    private String customerId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String mobileNo;
    private String address;
    private String accountStatus;
    private List<String> customerAddressList;
    private String occupation;
    private String nominee;
}
