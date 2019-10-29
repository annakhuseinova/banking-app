package com.annakhuseinova.bankingapp.repositories.AccountRepositoryImplementation;

import com.annakhuseinova.bankingapp.domain.Account;
import com.annakhuseinova.bankingapp.domain.AccountType;
import com.annakhuseinova.bankingapp.repositories.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountRepositoryImpl implements AccountRepository {

    private static ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    @PostConstruct
    public void populateAccounts(){

        Account account = new Account("1335003010", new BigDecimal("1000.00"), AccountType.CURRENT);
        Account account1 = new Account("1419262893", new BigDecimal("400.00"), AccountType.SAVINGS);
        accounts.put("1335003010", account);
        accounts.put("1419262893", account1);
    }

    @Override
    @Transactional
    public Optional<Account> findAccountByAccountNumber(String accountNumber) {

        return Optional.ofNullable(accounts.get(accountNumber));
    }

    @Override
    @Transactional
    public Account updateAccount(Account account) {

        accounts.replace(account.getAccountNumber(), account);
        return account;
    }

    @Override
    @Transactional
    public Account saveAccount(Account account) {

        accounts.putIfAbsent(account.getAccountNumber(), account);
        return account;
    }
}
