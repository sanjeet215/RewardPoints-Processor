package com.iccpl.transaction.processor.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardPointsBreak {
    private String transactionNumber;
    private int rewardPoints;
    private LocalDateTime dateTime;
}
