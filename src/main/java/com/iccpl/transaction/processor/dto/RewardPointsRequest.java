package com.iccpl.transaction.processor.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardPointsRequest {

    private String creditCardNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
