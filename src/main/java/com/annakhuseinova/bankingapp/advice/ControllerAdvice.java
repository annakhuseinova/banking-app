package com.annakhuseinova.bankingapp.advice;

import com.annakhuseinova.bankingapp.exceptions.AccountNotFoundException;
import com.annakhuseinova.bankingapp.exceptions.NotEnoughFundsOnAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidArgumentException(MethodArgumentNotValidException exception){

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.warn("Ошибка валидации : {}", errorMessage);
        });

        return errors;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAccountNotFoundException(AccountNotFoundException exception){

        log.warn("{}", exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(NotEnoughFundsOnAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNotEnoughFundsOnAccountException(NotEnoughFundsOnAccountException exception){

        log.warn("{}", exception.getMessage());
        return exception.getMessage();
    }

}
