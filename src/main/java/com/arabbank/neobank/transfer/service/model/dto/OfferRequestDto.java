package com.arabbank.neobank.transfer.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OfferRequestDto {
    private BigDecimal amount;
    private String customerId;
}
