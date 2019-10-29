package com.annakhuseinova.bankingapp.repositories;

import com.annakhuseinova.bankingapp.domain.Account;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findAccountByAccountNumber(String accountNumber);

    Account updateAccount(Account account);

    Account saveAccount(Account account);
}
