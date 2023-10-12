package com.arabbank.neobank.transfer.service.controller;

import com.arabbank.neobank.transfer.service.model.dto.TransferRequestDto;
import com.arabbank.neobank.transfer.service.model.dto.TransferResponseDto;
import com.arabbank.neobank.transfer.service.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class TransactionServiceController {
    @Autowired
    private TransferService transferService;

    @GetMapping("/get-transfer")
    public TransferResponseDto getTransferEntity(@RequestParam String transactionID) throws Exception {
        return transferService.getByTransactionId(transactionID);
    }

    @GetMapping("/get-by-date")
    public List<TransferResponseDto> getTransferByAccountNumberAndDateTime(@RequestParam String senderAccountNumber, @RequestParam String dateTime) throws Exception {
        return transferService.getByAccountNumberAndAmount(senderAccountNumber, dateTime);
    }

    @PatchMapping("/payment-status/{transactionID}/success")
    public void getTransferByAccountNumberAndAmount(@PathVariable String transactionID) throws Exception {
        transferService.paymentSuccess(transactionID);
    }


    @PostMapping("/save-transfer")
    public void saveTransaction(@RequestBody TransferRequestDto transferRequestDto) throws IOException {
        transferService.saveTransfer(transferRequestDto);
    }

}
