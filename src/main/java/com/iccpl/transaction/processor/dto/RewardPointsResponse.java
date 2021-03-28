package com.iccpl.transaction.processor.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardPointsResponse {

    private String creditCardNumber;
    private int lifeTimePoints;
    private List<RewardPointsBreak> rewardPoints;
}
