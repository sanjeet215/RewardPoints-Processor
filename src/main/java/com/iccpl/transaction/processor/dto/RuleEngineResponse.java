package com.iccpl.transaction.processor.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleEngineResponse {

    private int rewardPoints;
    private BigDecimal totalAmount; // Actual incoming amount, transaction amount
    private BigDecimal balanceAmount; // Remaining amount after point calculation
    private String ruleName;
}
