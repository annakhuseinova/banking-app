package com.annakhuseinova.bankingapp.services.TransactionServiceImplementation;

import com.annakhuseinova.bankingapp.domain.Account;
import com.annakhuseinova.bankingapp.domain.Transaction;
import com.annakhuseinova.bankingapp.dto.TransactionDto;
import com.annakhuseinova.bankingapp.dto.TransferDto;
import com.annakhuseinova.bankingapp.exceptions.AccountNotFoundException;
import com.annakhuseinova.bankingapp.exceptions.NotEnoughFundsOnAccountException;
import com.annakhuseinova.bankingapp.mappers.TransactionMapper;
import com.annakhuseinova.bankingapp.repositories.TransactionRepository;
import com.annakhuseinova.bankingapp.services.AccountService;
import com.annakhuseinova.bankingapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountService accountService;
    private static long nextTransactionId = 12322375;
    private TransactionMapper transactionMapper;

    @Autowired
    public void setTransactionMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Autowired
    public static void setNextTransactionId(long nextTransactionId) {
        TransactionServiceImpl.nextTransactionId = nextTransactionId;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    private long generateNextTransactionId(){

        return ++ nextTransactionId;
    }

    @Transactional
    public Transaction saveTransaction(Transaction transaction){

       return transactionRepository.saveTransaction(transaction);
    }

    public List<TransactionDto> getAllTransactions() {

        return transactionRepository.findAllTransactions().stream().map(transaction ->
                transactionMapper.convertTransactionEntityIntoTransactionDto(transaction)).collect(Collectors.toList());
    }

    @Transactional
    public void transferFundsBetweenAccounts(TransferDto transferDto) throws AccountNotFoundException, NotEnoughFundsOnAccountException {

        Account fromAccount = accountService.findAccountByAccountNumber(transferDto.getFromAccount()).orElseThrow(()
                -> new AccountNotFoundException("Счет " + transferDto.getFromAccount() + " не найден"));
        Account toAccount = accountService.findAccountByAccountNumber(transferDto.getToAccount()).orElseThrow(()->
                new AccountNotFoundException("Счет " + transferDto.getToAccount() + " не найден"));

        if (fromAccount.getBalance().compareTo(transferDto.getAmount()) < 0){

            throw new NotEnoughFundsOnAccountException("Невозможно осуществить перевод. Недостаточно средств на счете");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferDto.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transferDto.getAmount()));
        Transaction transaction = new Transaction(
                generateNextTransactionId(),
                transferDto.getFromAccount(),
                transferDto.getToAccount(),
                transferDto.getAmount(),
                LocalDateTime.now());
        saveTransaction(transaction);
        accountService.updateAccount(fromAccount);
        accountService.updateAccount(toAccount);

    }
}
