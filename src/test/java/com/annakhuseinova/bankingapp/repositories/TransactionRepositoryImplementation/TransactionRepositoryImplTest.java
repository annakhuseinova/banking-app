package com.annakhuseinova.bankingapp.repositories.TransactionRepositoryImplementation;

import com.annakhuseinova.bankingapp.domain.Transaction;
import com.annakhuseinova.bankingapp.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TransactionRepositoryImplTest {

    private TransactionRepository transactionRepository;
    Transaction transaction = Transaction.builder().date(LocalDateTime.now()).amount(new BigDecimal("1000.00"))
            .toAccount("140140140").fromAccount("130130130").id(1401401401L).build();

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Test
    @Rollback
    public void whenSavedTransaction_thenReturnSavedTransaction(){


        assertEquals(transaction, transactionRepository.saveTransaction(transaction));

    }

    @Test
    @Rollback
    public void whenFindAllTransactions_thenContainSavedTransaction(){

        transactionRepository.saveTransaction(transaction);
        assertTrue(transactionRepository.findAllTransactions().contains(transaction));
    }
}
