package com.annakhuseinova.bankingapp.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private Long id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private LocalDateTime date;
}
