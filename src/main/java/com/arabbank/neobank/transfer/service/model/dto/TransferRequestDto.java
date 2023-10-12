package com.arabbank.neobank.transfer.service.model.dto;

import com.arabbank.neobank.transfer.service.model.entity.Currency;
import com.arabbank.neobank.transfer.service.model.entity.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {
    private String senderAccountNumber;
    private String senderName;
    private BigDecimal amount;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private Currency currency;
    private PaymentMethod paymentMethod;
    private String comments;
}
