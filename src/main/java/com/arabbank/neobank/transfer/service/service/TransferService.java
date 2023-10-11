package com.arabbank.neobank.transfer.service.service;

import com.arabbank.neobank.transfer.service.model.dto.CustomerProfileResponseDTO;
import com.arabbank.neobank.transfer.service.model.dto.TransferRequestDto;
import com.arabbank.neobank.transfer.service.model.dto.TransferResponseDto;
import com.arabbank.neobank.transfer.service.model.entity.PaymentStatus;
import com.arabbank.neobank.transfer.service.model.entity.TransferEntity;
import com.arabbank.neobank.transfer.service.repository.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {
    @Autowired
    private TransferRepo transferRepo;

    @Autowired
    private RestClient client;

    public TransferResponseDto getByTransactionId(String transactionID) throws Exception {
        Optional<TransferEntity> transferRef = transferRepo.findByTransactionID(transactionID);
        if (transferRef.isPresent()) {
            TransferEntity transferEntity = transferRef.get();
            TransferResponseDto transferResponseDto = new TransferResponseDto();

            transferResponseDto.setAmount(transferEntity.getAmount());
            transferResponseDto.setTransactionID(transferEntity.getTransactionID());
            transferResponseDto.setAccountHolderName(transferEntity.getAccountHolderName());
            transferResponseDto.setAccountNumber(transferEntity.getAccountNumber());
            transferResponseDto.setBeneficiaryName(transferEntity.getBeneficiaryName());
            transferResponseDto.setPaymentMethod(transferEntity.getPaymentMethod());
            transferResponseDto.setComments(transferEntity.getComments());
            transferResponseDto.setCurrency(transferEntity.getCurrency());
            transferResponseDto.setCharges(transferEntity.getCharges());
            transferResponseDto.setBeneficiaryAccountNumber(transferEntity.getBeneficiaryAccountNumber());
            transferResponseDto.setDateTime(transferEntity.getDateTime());
            transferResponseDto.setPaymentStatus(transferEntity.getPaymentStatus());

            return transferResponseDto;
        }
        throw new Exception("Not found");
    }

    public List<TransferResponseDto> getByAccountNumberAndAmount(String accountNumber, String dateTime) throws Exception {
        List<TransferResponseDto> responseDtoList = new ArrayList<>();
        LocalDateTime date = parseDateTime(dateTime);

        List<TransferEntity> transferRef = transferRepo.findByAccountNumberAndDateTime(accountNumber, date);
        for (TransferEntity transferEntity :
                transferRef) {
            TransferResponseDto transferResponseDto = new TransferResponseDto();
            transferResponseDto.setAmount(transferEntity.getAmount());
            transferResponseDto.setTransactionID(transferEntity.getTransactionID());
            transferResponseDto.setAccountHolderName(transferEntity.getAccountHolderName());
            transferResponseDto.setAccountNumber(transferEntity.getAccountNumber());
            transferResponseDto.setBeneficiaryName(transferEntity.getBeneficiaryName());
            transferResponseDto.setPaymentMethod(transferEntity.getPaymentMethod());
            transferResponseDto.setComments(transferEntity.getComments());
            transferResponseDto.setCurrency(transferEntity.getCurrency());
            transferResponseDto.setCharges(transferEntity.getCharges());
            transferResponseDto.setBeneficiaryAccountNumber(transferEntity.getBeneficiaryAccountNumber());
            transferResponseDto.setDateTime(transferEntity.getDateTime());
            transferResponseDto.setPaymentStatus(transferEntity.getPaymentStatus());

            responseDtoList.add(transferResponseDto);
        }
        return responseDtoList;
    }

    public void saveTransfer(TransferRequestDto transferRequestDto) {
        TransferEntity transferEntity = new TransferEntity();

        try {
            getCustomer(transferRequestDto.getCustomerId());
        } catch (Exception e) {
            e.printStackTrace();

        }

        transferEntity.setAmount(transferRequestDto.getAmount());
        transferEntity.setTransactionID(transferRequestDto.getTransactionID());
        transferEntity.setAccountHolderName(transferRequestDto.getAccountHolderName());
        transferEntity.setAccountNumber(transferRequestDto.getAccountNumber());
        transferEntity.setBeneficiaryName(transferRequestDto.getBeneficiaryName());
        transferEntity.setPaymentMethod(transferRequestDto.getPaymentMethod());
        transferEntity.setComments(transferRequestDto.getComments());
        transferEntity.setCurrency(transferRequestDto.getCurrency());
        transferEntity.setCharges(transferRequestDto.getCharges());
        transferEntity.setBeneficiaryAccountNumber(transferRequestDto.getBeneficiaryAccountNumber());
        transferEntity.setDateTime(LocalDateTime.now());

        transferRepo.save(transferEntity);
    }

    public void paymentSuccess(String transactionID) {
        Optional<TransferEntity> transferRef = transferRepo.findByTransactionID(transactionID);
        if (transferRef.isPresent()) {
            TransferEntity transferEntity = transferRef.get();
            transferEntity.setPaymentStatus(PaymentStatus.SUCCESS);
            transferRepo.save(transferEntity);
        }
    }

    private CustomerProfileResponseDTO getCustomer(String customerId) throws Exception {
        CustomerProfileResponseDTO customerProfileByCustomerId = client.getCustomerProfileByCustomerId(customerId);
        if (customerProfileByCustomerId == null) {
            throw new Exception("NOT FOUND");
        }
        return customerProfileByCustomerId;
    }

    private LocalDateTime parseDateTime(String dateTime) throws Exception {
        try {
            return LocalDateTime.parse(dateTime);
        } catch (Exception e) {
            throw new Exception("Invalid format");
        }
    }

}
