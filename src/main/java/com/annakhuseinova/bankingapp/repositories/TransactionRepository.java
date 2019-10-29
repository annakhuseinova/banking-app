package com.annakhuseinova.bankingapp.repositories;

import com.annakhuseinova.bankingapp.domain.Transaction;
import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAllTransactions();

    Transaction saveTransaction(Transaction transaction);
}
