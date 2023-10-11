package com.arabbank.neobank.transfer.service.model.dto;

import com.arabbank.neobank.transfer.service.model.entity.Currency;
import com.arabbank.neobank.transfer.service.model.entity.PaymentMethod;
import com.arabbank.neobank.transfer.service.model.entity.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferRequestDto {
    private String customerId;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal amount;
    private BigDecimal charges;
    private String transactionID;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private Currency currency;
    private PaymentMethod paymentMethod;
    private String comments;
}
