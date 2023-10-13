package com.arabbank.neobank.transfer.service.model.dto;

import com.arabbank.neobank.transfer.service.model.entity.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDto {
    private String accountNumber;
    private String customerId;
    private BigDecimal balance;
    private Currency currency;
    private String transactionLimit;
}
