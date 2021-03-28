package com.iccpl.transaction.processor.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LifeTImePoints {

    @Id
    private String creditCardNumber;
    private BigDecimal balanceUnCalculatedAmount;
    private int lifeTimePoints;
}
