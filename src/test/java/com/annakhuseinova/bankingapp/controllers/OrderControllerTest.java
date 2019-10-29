package com.annakhuseinova.bankingapp.controllers;

import com.annakhuseinova.bankingapp.dto.TransactionDto;
import com.annakhuseinova.bankingapp.dto.TransferDto;
import com.annakhuseinova.bankingapp.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class OrderControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Checking if business logic is invoked and 200 http-status is returned when the input is valid in transferFundsBetweenAccounts method")
    public void whenValidInput_thenInputIsPassedToBusinessLogic() throws Exception {

        TransferDto transferDto = new TransferDto("140140140", "130130130", new BigDecimal("1000.00"));
        this.mockMvc.perform(post("/order/transfer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transferDto )))
                .andExpect(status().isOk());
        ArgumentCaptor<TransferDto> transferDtoArgumentCaptor = ArgumentCaptor.forClass(TransferDto.class);
        verify(transactionService, times(1)).transferFundsBetweenAccounts(transferDtoArgumentCaptor.capture());
        assertThat(transferDtoArgumentCaptor.getValue().getAmount()).isEqualTo(new BigDecimal("1000.00"));
        assertThat(transferDtoArgumentCaptor.getValue().getToAccount()).isEqualTo("140140140");
        assertThat(transferDtoArgumentCaptor.getValue().getFromAccount()).isEqualTo("130130130");
    }

    @Test
    @DisplayName("Checking if validation fails as expected in transferFundsBetweenAccounts method")
    public void whenInvalidInput_thenValidationFailsAsExpected() throws Exception {

        TransferDto transferDto = new TransferDto(null, null, null);
        this.mockMvc.perform(post("/order/transfer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transferDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Checking if getTransferHistory method produces TransactionDtos in json")
    void whenGetTransferHistory_thenReturnTransactionDtoList() throws Exception {

        TransactionDto transactionDto = new TransactionDto(100000L, "140140140", "130130130", new BigDecimal("1000.00"), "14.01.2019");
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactionDtos.add(transactionDto);
        when(transactionService.getAllTransactions()).thenReturn(transactionDtos);
        this.mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(transactionDto.getId().intValue())))
                .andExpect(jsonPath("$.[0].fromAccount", is(transactionDto.getFromAccount())))
                .andExpect(jsonPath("$.[0].toAccount", is(transactionDto.getToAccount())));
        verify(this.transactionService, times(1)).getAllTransactions();

    }
}
