package com.annakhuseinova.bankingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransferDto {

    @NotBlank(message = "Вы должны предоставить номер целевого счета")
    private String toAccount;
    @NotBlank(message = "Вы должны предоставить номер исходящего счета")
    private String fromAccount;
    @DecimalMin(message = "Вы должны определить сумму перевода", value = "0.00", inclusive = false)
    private BigDecimal amount;
}
