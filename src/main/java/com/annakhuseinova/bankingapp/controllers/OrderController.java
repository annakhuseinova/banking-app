package com.annakhuseinova.bankingapp.controllers;

import com.annakhuseinova.bankingapp.dto.TransactionDto;
import com.annakhuseinova.bankingapp.dto.TransferDto;
import com.annakhuseinova.bankingapp.services.TransactionService;
import com.annakhuseinova.bankingapp.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private TransactionService transactionService;

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<ResponseMessage> transferFundsBetweenAccounts(@Valid @RequestBody TransferDto transferDto){

         transactionService.transferFundsBetweenAccounts(transferDto);
         return new ResponseEntity<>(new ResponseMessage("Успешно переведено " + transferDto.getAmount() + " рублей (рубля) со счета "
                 + transferDto.getFromAccount() + " на счет " + transferDto.getToAccount()), HttpStatus.OK);
    }

    @GetMapping("/history")
    public List<TransactionDto> getTransferHistory(){

        return transactionService.getAllTransactions();
    }
}
