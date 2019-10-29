package com.annakhuseinova.bankingapp.mappers;

import com.annakhuseinova.bankingapp.domain.Transaction;
import com.annakhuseinova.bankingapp.dto.TransactionDto;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionMapper {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public TransactionDto convertTransactionEntityIntoTransactionDto(Transaction transaction){

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(dateTimeFormatter.format(transaction.getDate()));
        transactionDto.setFromAccount(transaction.getFromAccount());
        transactionDto.setToAccount(transaction.getToAccount());
        return transactionDto;
    }
}
