package com.annakhuseinova.bankingapp.services;

import com.annakhuseinova.bankingapp.dto.TransactionDto;
import com.annakhuseinova.bankingapp.dto.TransferDto;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> getAllTransactions();
    void transferFundsBetweenAccounts(TransferDto transferDto);
}
