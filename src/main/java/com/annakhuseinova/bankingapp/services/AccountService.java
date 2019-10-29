package com.annakhuseinova.bankingapp.services;

import com.annakhuseinova.bankingapp.domain.Account;
import java.util.Optional;
public interface AccountService {

     Optional<Account> findAccountByAccountNumber(String accountNumber);

     Account updateAccount(Account account);

     Account saveAccount(Account account);
}
