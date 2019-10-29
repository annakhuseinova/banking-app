package com.annakhuseinova.bankingapp.mappers;

import com.annakhuseinova.bankingapp.domain.Transaction;
import com.annakhuseinova.bankingapp.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionMapperTest {

    private TransactionMapper transactionMapper = new TransactionMapper();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Test
    public void checkIfConversionFromTransactionEntityIntoTransactionDtoIsCorrect(){

        Transaction transaction = new Transaction();
        transaction.setId(104104L);
        transaction.setAmount(new BigDecimal("1000.00"));
        transaction.setDate(LocalDateTime.of(2019, Month.OCTOBER, 27, 12,50,30));
        transaction.setFromAccount("140140140");
        transaction.setToAccount("130130130");

        TransactionDto transactionDto = transactionMapper.convertTransactionEntityIntoTransactionDto(transaction);
        assertEquals(transaction.getId(), transactionDto.getId());
        assertEquals(transaction.getAmount(), transactionDto.getAmount());
        assertEquals(transaction.getFromAccount(), transactionDto.getFromAccount());
        assertEquals(transaction.getToAccount(), transactionDto.getToAccount());
        assertEquals(transaction.getDate(), LocalDateTime.parse(transactionDto.getDate(), dateTimeFormatter));
    }

}
