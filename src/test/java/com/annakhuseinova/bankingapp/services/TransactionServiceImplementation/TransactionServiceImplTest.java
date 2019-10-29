package com.annakhuseinova.bankingapp.services.TransactionServiceImplementation;

import com.annakhuseinova.bankingapp.domain.Account;
import com.annakhuseinova.bankingapp.domain.AccountType;
import com.annakhuseinova.bankingapp.domain.Transaction;
import com.annakhuseinova.bankingapp.dto.TransactionDto;
import com.annakhuseinova.bankingapp.dto.TransferDto;
import com.annakhuseinova.bankingapp.exceptions.NotEnoughFundsOnAccountException;
import com.annakhuseinova.bankingapp.mappers.TransactionMapper;
import com.annakhuseinova.bankingapp.repositories.TransactionRepositoryImplementation.TransactionRepositoryImpl;
import com.annakhuseinova.bankingapp.services.AccountServiceImplementation.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepositoryImpl transactionRepository;
    @Mock
    private TransactionMapper transactionMapper1;

    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionMapper transactionMapper = new TransactionMapper();

    private Transaction transaction = Transaction.builder().id(14560105L).fromAccount("130130130").toAccount("140140140")
            .amount(new BigDecimal("1000.00")).date(LocalDateTime.now()).build();

    private TransferDto transferDto = TransferDto.builder().toAccount("140140140").fromAccount("130130130")
            .amount(new BigDecimal("1000.00")).build();

    @Test
    void whenSaveTransaction_thenReturnSavedTransaction() {

        when(transactionRepository.saveTransaction(transaction)).thenReturn(transaction);
        assertThat(transactionService.saveTransaction(transaction)).isEqualTo(transaction);
    }

    @Test
    void whenGetAllTransactions_thenReturnListOfTransactionDtos() {

        List<Transaction> transactions = new ArrayList<>();
        TransactionDto transactionDto = transactionMapper.convertTransactionEntityIntoTransactionDto(transaction);
        transactions.add(transaction);
        when(transactionRepository.findAllTransactions()).thenReturn(transactions);
        when(transactionMapper1.convertTransactionEntityIntoTransactionDto(transaction)).thenReturn(transactionDto);
        List<TransactionDto> transactionDtos = transactions.stream().map(transaction1 ->
                transactionMapper1.convertTransactionEntityIntoTransactionDto(transaction1)).collect(Collectors.toList());
        assertThat(transactionService.getAllTransactions()).isEqualTo(transactionDtos);

    }

    @Test
    void whenProvidedValidTransferData_thenTransferEffectedCorrectly() {

      TransferDto transferDto = TransferDto.builder().amount(new BigDecimal("200.00")).fromAccount("1335003010")
              .toAccount("1419262893").build();
      Account fromAccount = new Account("1335003010", new BigDecimal("1000.00"), AccountType.CURRENT);
      Account toAccount = new Account("1419262893", new BigDecimal("200.00"), AccountType.SAVINGS);
      when(accountService.findAccountByAccountNumber("1335003010")).thenReturn(Optional.of(fromAccount));
      when(accountService.findAccountByAccountNumber("1419262893")).thenReturn(Optional.of(toAccount));
      BigDecimal expectedAmountOnFromAccount = fromAccount.getBalance().subtract(transferDto.getAmount());
      BigDecimal expectedAmountOnToAccount = toAccount.getBalance().add(transferDto.getAmount());
      transactionService.transferFundsBetweenAccounts(transferDto);
      assertThat(expectedAmountOnFromAccount).isEqualTo(accountService.findAccountByAccountNumber("1335003010").get().getBalance());
      assertThat(expectedAmountOnToAccount).isEqualTo(accountService.findAccountByAccountNumber("1419262893").get().getBalance());
      verify(accountService, times(1)).updateAccount(fromAccount);
      verify(accountService, times(1)).updateAccount(toAccount);
      verify(transactionRepository, times(1)).saveTransaction(any());
    }

    @Test
    void whenGiveExceedingAmountToBeTransferred_thenThrowNotEnoughFundsOnAccountException(){

        Account account = Account.builder().accountNumber("1401401401").accountType(AccountType.CURRENT)
                .balance(new BigDecimal("0.00")).build();
        when(accountService.findAccountByAccountNumber(anyString())).thenReturn(Optional.of(account));
        assertThrows(NotEnoughFundsOnAccountException.class, ()->{

            transactionService.transferFundsBetweenAccounts(transferDto);
        });
    }
}
