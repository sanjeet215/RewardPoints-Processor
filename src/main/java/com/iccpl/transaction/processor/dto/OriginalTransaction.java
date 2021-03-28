package com.iccpl.transaction.processor.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalTransaction {

    private String creditCardNumber;
    private String creditCardType;
    private String transactionNumber;
    private String description;
    private String category;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
}
