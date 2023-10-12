package com.arabbank.neobank.transfer.service.repository;

import com.arabbank.neobank.transfer.service.model.entity.TransferEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepo extends MongoRepository<TransferEntity, String> {
    Optional<TransferEntity> findByTransactionID(String transactionID);

    List<TransferEntity> findBySenderAccountNumberAndDateTime(String senderAccountNumber, LocalDateTime dateTime);
}
