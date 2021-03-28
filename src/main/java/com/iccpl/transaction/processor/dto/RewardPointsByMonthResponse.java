package com.iccpl.transaction.processor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardPointsByMonthResponse {
    private String month;
    private int points;
}
