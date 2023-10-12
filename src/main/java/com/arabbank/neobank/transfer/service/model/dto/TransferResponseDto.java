package com.arabbank.neobank.transfer.service.model.dto;

import com.arabbank.neobank.transfer.service.model.entity.Currency;
import com.arabbank.neobank.transfer.service.model.entity.PaymentMethod;
import com.arabbank.neobank.transfer.service.model.entity.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferResponseDto {
    private String senderAccountNumber;
    private String senderName;
    private BigDecimal amount;
    private BigDecimal charges;
    private LocalDateTime dateTime;
    private String transactionID;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private PaymentStatus paymentStatus;
    private Currency currency;
    private PaymentMethod paymentMethod;
    private String comments;
}
