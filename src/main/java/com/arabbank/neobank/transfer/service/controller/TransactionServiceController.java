package com.arabbank.neobank.transfer.service.controller;

import com.arabbank.neobank.transfer.service.model.dto.TransferRequestDto;
import com.arabbank.neobank.transfer.service.model.dto.TransferResponseDto;
import com.arabbank.neobank.transfer.service.model.entity.TransferEntity;
import com.arabbank.neobank.transfer.service.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TransactionServiceController {
    @Autowired
    private TransferService transferService;

    @GetMapping("/transaction")
    public TransferResponseDto getTransferEntity(@RequestParam String transactionID) throws Exception {
        return transferService.getByTransactionId(transactionID);
    }

    @GetMapping("/get-by-date")
    public List<TransferResponseDto> getTransferByAccountNumberAndDateTime(@RequestParam String accountNumber, @RequestParam String dateTime) throws Exception {
        return transferService.getByAccountNumberAndAmount(accountNumber, dateTime);
    }

    @PatchMapping("/payment-status/{transactionID}/success")
    public void getTransferByAccountNumberAndAmount(@PathVariable String transactionID) throws Exception {
        transferService.paymentSuccess(transactionID);
    }


    @PostMapping("/transaction")
    public void saveTransaction(@RequestBody TransferRequestDto transferServiceEntity) {
        transferService.saveTransfer(transferServiceEntity);
    }

}
