package com.annakhuseinova.bankingapp.exceptions;

public class NotEnoughFundsOnAccountException extends RuntimeException {

    public NotEnoughFundsOnAccountException(String message) {
        super(message);
    }
}
