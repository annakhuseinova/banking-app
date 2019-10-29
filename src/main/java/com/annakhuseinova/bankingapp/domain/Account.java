package com.annakhuseinova.bankingapp.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
}
