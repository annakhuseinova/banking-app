package com.annakhuseinova.bankingapp.repositories.TransactionRepositoryImplementation;

import com.annakhuseinova.bankingapp.domain.Transaction;
import com.annakhuseinova.bankingapp.repositories.TransactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TransactionRepositoryImpl implements TransactionRepository {

    private static CopyOnWriteArrayList<Transaction> transactions = new CopyOnWriteArrayList<>();

    @Override
    @Transactional
    public List<Transaction> findAllTransactions(){

        return transactions;
    }

    @Override
    @Transactional
    public Transaction saveTransaction(Transaction transaction) {

        transactions.add(transaction);
        return transaction;
    }
}
