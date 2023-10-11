package com.arabbank.neobank.transfer.service.model.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transfer-details")
@Data
public class TransferEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String transactionID;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal amount;
    private BigDecimal charges;
    private LocalDateTime dateTime;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private PaymentStatus paymentStatus;
    private Currency currency;
    private PaymentMethod paymentMethod;
    private String comments;
    private String approvedBy;
}
