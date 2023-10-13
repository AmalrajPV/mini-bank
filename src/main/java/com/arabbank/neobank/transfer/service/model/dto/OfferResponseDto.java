package com.arabbank.neobank.transfer.service.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferResponseDto {
    private BigDecimal amount;
    private String customerId;
    private String discountAmount;
    private String offerId;
}
