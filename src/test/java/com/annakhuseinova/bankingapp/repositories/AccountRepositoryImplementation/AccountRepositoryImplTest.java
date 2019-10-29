package com.annakhuseinova.bankingapp.repositories.AccountRepositoryImplementation;

import com.annakhuseinova.bankingapp.domain.Account;
import com.annakhuseinova.bankingapp.domain.AccountType;
import com.annakhuseinova.bankingapp.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class AccountRepositoryImplTest {

   private AccountRepository accountRepository;
   private Account account = Account.builder().accountType(AccountType.CURRENT).balance(new BigDecimal("1000.00"))
           .accountNumber("252525").build();

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Test
    @Rollback
    public void whenUpdateAccount_thenReturnUpdatedAccount(){

        assertEquals(account, accountRepository.updateAccount(account));
    }

    @Test
    public void whenFindExistingAccountByAccountNumber_thenReturnIt(){

        assertTrue(accountRepository.findAccountByAccountNumber("1335003010").isPresent());

    }
}
