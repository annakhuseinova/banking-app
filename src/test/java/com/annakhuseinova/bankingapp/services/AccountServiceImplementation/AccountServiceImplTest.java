package com.annakhuseinova.bankingapp.services.AccountServiceImplementation;

import com.annakhuseinova.bankingapp.domain.Account;
import com.annakhuseinova.bankingapp.domain.AccountType;
import com.annakhuseinova.bankingapp.exceptions.AccountNotFoundException;
import com.annakhuseinova.bankingapp.repositories.AccountRepositoryImplementation.AccountRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepositoryImpl accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void whenGivenValidAccountNumber_thenReturnCorrectAccount(){

        Account account = new Account();
        account.setAccountNumber("140140140");
        when(accountRepository.findAccountByAccountNumber(account.getAccountNumber())).thenReturn(Optional.of(account));
        assertThat(accountService.findAccountByAccountNumber(account.getAccountNumber())).isEqualTo(Optional.of(account));
    }

    @Test
    public void whenGivenInvalidAccountNumber_thenThrowAccountNotFoundException(){

        when(accountRepository.findAccountByAccountNumber(anyString())).thenThrow(AccountNotFoundException.class);
        assertThrows(AccountNotFoundException.class, ()-> {
            accountService.findAccountByAccountNumber(anyString());
        });
    }

    @Test
    public void whenSaveAccount_thenReturnSavedAccount(){

        Account account = new Account("42424242", new BigDecimal("414.00"), AccountType.CURRENT);
        when(accountRepository.saveAccount(account)).thenReturn(account);
        assertThat(accountService.saveAccount(account)).isEqualTo(account);

    }

    @Test
    public void whenUpdateAccount_thenReturnUpdatedAccount(){

        Account account = new Account("140140140", new BigDecimal("1000.00"), AccountType.CURRENT);
        when(accountRepository.updateAccount(account)).thenReturn(account);
        assertThat(accountService.updateAccount(account)).isEqualTo(account);
    }

}
