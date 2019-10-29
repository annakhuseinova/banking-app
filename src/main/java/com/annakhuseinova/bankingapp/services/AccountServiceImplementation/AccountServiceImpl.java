package com.annakhuseinova.bankingapp.services.AccountServiceImplementation;

import com.annakhuseinova.bankingapp.domain.Account;
import com.annakhuseinova.bankingapp.repositories.AccountRepository;
import com.annakhuseinova.bankingapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findAccountByAccountNumber(String accountNumber){

        return accountRepository.findAccountByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public Account saveAccount(Account account) {

        return accountRepository.saveAccount(account);
    }

    @Override
    @Transactional
    public Account updateAccount(Account account) {

       return accountRepository.updateAccount(account);
    }
}
