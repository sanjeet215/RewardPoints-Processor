package com.iccpl.transaction.processor.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction_data")
public class TransactionBase {

    private String creditCardNumber;
    private String transactionNumber;
    private LocalDateTime transactionTimeStamp;
    private TransactionCategory transactionCategory;
    BigDecimal transactionAmount;
    private String description;
    private TransactionStatus transactionStatus;
    private String failureReason;
    private String creditCardType;
    private Integer rewardPoints;

    private String ruleCalculationName;
}
