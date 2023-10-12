package com.arabbank.neobank.transfer.service.service;

import com.arabbank.neobank.transfer.service.model.dto.*;
import com.arabbank.neobank.transfer.service.model.entity.PaymentStatus;
import com.arabbank.neobank.transfer.service.model.entity.TransferEntity;
import com.arabbank.neobank.transfer.service.repository.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
            transferResponseDto.setSenderName(transferEntity.getSenderName());
            transferResponseDto.setSenderAccountNumber(transferEntity.getSenderAccountNumber());
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

    public List<TransferResponseDto> getByAccountNumberAndAmount(String senderAccountNumber, String dateTime) throws Exception {
        List<TransferResponseDto> responseDtoList = new ArrayList<>();
        LocalDateTime date = parseDateTime(dateTime);

        List<TransferEntity> transferRef = transferRepo.findBySenderAccountNumberAndDateTime(senderAccountNumber, date);
        for (TransferEntity transferEntity : transferRef) {
            TransferResponseDto transferResponseDto = new TransferResponseDto();
            transferResponseDto.setAmount(transferEntity.getAmount());
            transferResponseDto.setTransactionID(transferEntity.getTransactionID());
            transferResponseDto.setSenderName(transferEntity.getSenderName());
            transferResponseDto.setSenderAccountNumber(transferEntity.getSenderAccountNumber());
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

    public TransferResponseDto saveTransfer(TransferRequestDto transferRequestDto) throws IOException {
        TransferEntity transferEntity = new TransferEntity();

        AccountResponseDto senderAccountDetails = getAccountDetails(transferRequestDto.getSenderAccountNumber());
        AccountResponseDto receiverAccountDetails = getAccountDetails(transferRequestDto.getBeneficiaryAccountNumber());
        if (senderAccountDetails == null || receiverAccountDetails == null || senderAccountDetails.getCustomerId() != null || receiverAccountDetails.getCustomerId() != null) {
            return null;
        }
        if (senderAccountDetails.getBalance().compareTo(transferRequestDto.getAmount()) < 0) {
            return null;
        }

        CustomerProfileResponseDTO senderProfileDto = getCustomer(senderAccountDetails.getCustomerId());
        CustomerProfileResponseDTO receiverProfileDto = getCustomer(receiverAccountDetails.getCustomerId());


        if (senderProfileDto != null || receiverProfileDto != null) {
            if (Objects.equals(senderProfileDto.getAccountStatus(), "Active")) {
                transferEntity.setAmount(transferRequestDto.getAmount());
                transferEntity.setTransactionID(randomTransferId());
                transferEntity.setSenderName(senderProfileDto.getName());
                transferEntity.setSenderAccountNumber(transferRequestDto.getSenderAccountNumber());
                transferEntity.setBeneficiaryName(receiverProfileDto.getName());
                transferEntity.setPaymentMethod(transferRequestDto.getPaymentMethod());
                transferEntity.setComments(transferRequestDto.getComments());
                transferEntity.setCurrency(transferRequestDto.getCurrency());
                transferEntity.setBeneficiaryAccountNumber(transferRequestDto.getBeneficiaryAccountNumber());
                transferEntity.setDateTime(LocalDateTime.now());
                transferEntity.setPaymentStatus(PaymentStatus.INITIALIZED);

                transferRepo.save(transferEntity);

                TransferFinalResponseDTO transferFinalResponseDTO = new TransferFinalResponseDTO();
                transferFinalResponseDTO.setTransferID(transferEntity.getTransactionID());
                transferFinalResponseDTO.setAmount(transferEntity.getAmount());
                transferFinalResponseDTO.setSenderAccountNumber(transferEntity.getSenderAccountNumber());
                transferFinalResponseDTO.setReceiverAccountNumber(transferEntity.getBeneficiaryAccountNumber());
                client.sendTransferDetails(transferFinalResponseDTO);

                TransferResponseDto transferResponseDto = new TransferResponseDto();
                transferResponseDto.setAmount(transferEntity.getAmount());
                transferResponseDto.setTransactionID(transferEntity.getTransactionID());
                transferResponseDto.setSenderName(transferEntity.getSenderName());
                transferResponseDto.setSenderAccountNumber(transferEntity.getSenderAccountNumber());
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
        }
        return null;
    }

    public void paymentSuccess(String transactionID) {
        Optional<TransferEntity> transferRef = transferRepo.findByTransactionID(transactionID);
        if (transferRef.isPresent()) {
            TransferEntity transferEntity = transferRef.get();
            transferEntity.setPaymentStatus(PaymentStatus.SUCCESS);
            transferRepo.save(transferEntity);
        }
    }

    public TransferFinalResponseDTO sendTransferDetails(TransferEntity transferEntity) {
        TransferFinalResponseDTO transferFinalResponseDTO = new TransferFinalResponseDTO();
        transferFinalResponseDTO.setAmount(transferEntity.getAmount());
        transferFinalResponseDTO.setTransferID(transferEntity.getTransactionID());
        transferFinalResponseDTO.setSenderAccountNumber(transferEntity.getSenderAccountNumber());
        transferFinalResponseDTO.setReceiverAccountNumber(transferEntity.getBeneficiaryAccountNumber());
        return transferFinalResponseDTO;
    }

    private CustomerProfileResponseDTO getCustomer(String customerId) {
        try {
            return client.getCustomerProfileByCustomerId(customerId);
        } catch (IOException e) {
            return null;
        }
    }

    private AccountResponseDto getAccountDetails(String accountNumber) {
        try {
            return client.getAccountByAccountNumber(accountNumber);
        } catch (Exception e) {
            return null;
        }


    }

    private LocalDateTime parseDateTime(String dateTime) throws Exception {
        try {
            return LocalDateTime.parse(dateTime);
        } catch (Exception e) {
            throw new Exception("Invalid format");
        }
    }

    private String randomTransferId() {
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        int random4DigitNumber = random.nextInt(max - min + 1) + min;
        return String.valueOf(random4DigitNumber);
    }

}
