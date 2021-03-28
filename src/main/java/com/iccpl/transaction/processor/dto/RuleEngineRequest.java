package com.iccpl.transaction.processor.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleEngineRequest {

    private BigDecimal amount;
    private String creditCardType;
    private LocalDateTime transactionDate;
}
