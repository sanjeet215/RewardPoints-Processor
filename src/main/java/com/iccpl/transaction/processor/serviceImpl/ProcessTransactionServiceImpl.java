package com.iccpl.transaction.processor.serviceImpl;

import com.iccpl.transaction.processor.domain.LifeTImePoints;
import com.iccpl.transaction.processor.domain.TransactionBase;
import com.iccpl.transaction.processor.domain.TransactionStatus;
import com.iccpl.transaction.processor.dto.OriginalTransaction;
import com.iccpl.transaction.processor.dto.RuleEngineRequest;
import com.iccpl.transaction.processor.dto.RuleEngineResponse;
import com.iccpl.transaction.processor.repository.LifeTimePointsRepository;
import com.iccpl.transaction.processor.repository.TransactionBaseRepository;
import com.iccpl.transaction.processor.service.ProcessTransactionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessTransactionServiceImpl implements ProcessTransactionService {

    @Autowired
    TransactionBaseRepository transactionBaseRepository;

    @Autowired
    LifeTimePointsRepository lifeTimePointsRepository;

    // Step 1: Process messages --> validate --> map the input transaction data to domain object.
    // Step 2: Invoke Rule engine to calculate reward points
    // Step 3: Store the final domain/Base object into database.

    @Override
    public void processTransactionEvaluateAndStore(OriginalTransaction originalTransaction) {

        TransactionBase transactionBase = new TransactionBase();

        // Validate and Copy data from dto to domain object.
        validateAndCreateDerivedTransaction(originalTransaction, transactionBase);

        //Get any amount which is pending to be evaluated
        BigDecimal amount = originalTransaction.getAmount();
        Optional<LifeTImePoints> lifeTImePoints = lifeTimePointsRepository.findByCreditCardNumber(originalTransaction.getCreditCardNumber());

        if (lifeTImePoints.isPresent()) {
            amount = amount.add(BigDecimal.valueOf(lifeTImePoints.get().getLifeTimePoints()));
        }


        //Invoke Rule Engine and get Reward Points
        RuleEngineResponse ruleEngineResponse = invokeRuleEngineAndGetRewardPoints(amount,
                originalTransaction.getCreditCardType(), originalTransaction.getTransactionTime());
        transactionBase.setRewardPoints(ruleEngineResponse.getRewardPoints());
        transactionBase.setRuleCalculationName(ruleEngineResponse.getRuleName());


        //Store final domain/base object
        transactionBaseRepository.save(transactionBase);
        lifeTimePointsRepository.save(new LifeTImePoints(originalTransaction.getCreditCardNumber(),
                ruleEngineResponse.getBalanceAmount(), ruleEngineResponse.getRewardPoints()));
    }

    private RuleEngineResponse invokeRuleEngineAndGetRewardPoints(BigDecimal amount, String creditCardType, LocalDateTime transactionTime) {
        RuleEngineResponse ruleEngineResponse = new RuleEngineResponse();
        RuleEngineRequest request = new RuleEngineRequest(amount,creditCardType,transactionTime);
        return ruleEngineResponse;
    }

    private void validateAndCreateDerivedTransaction(OriginalTransaction originalTransaction, TransactionBase transactionBase) {

        // validate creditCardNumber
        if (!isCreditCardNumberValid(originalTransaction.getCreditCardNumber())) {
            transactionBase.setTransactionStatus(TransactionStatus.FAILED);
            transactionBase.setFailureReason("Invalid credit card number");
            logFailedTransaction(originalTransaction, transactionBase);
        }

        // Validate transactionNumber
        if (!isTransactionNumberValid(originalTransaction.getTransactionNumber())) {
            transactionBase.setTransactionStatus(TransactionStatus.FAILED);
            transactionBase.setFailureReason("Invalid transaction number");
            logFailedTransaction(originalTransaction, transactionBase);
        }

        //Similarly Other validations can be done here.
    }

    // transaction Number validation logic goes here. // For now only null check.
    private boolean isTransactionNumberValid(String transactionNumber) {
        if (transactionNumber != null) {
            return true;
        }
        return false;
    }

    // Log transaction as failed transaction.
    public void logFailedTransaction(OriginalTransaction originalTransaction, TransactionBase transactionBase) {
        transactionBase.setTransactionAmount(originalTransaction.getAmount());
        transactionBase.setDescription(originalTransaction.getDescription());
        transactionBase.setRewardPoints(0);
        transactionBase.setTransactionNumber(originalTransaction.getTransactionNumber());
        transactionBase.setTransactionTimeStamp(originalTransaction.getTransactionTime());
        transactionBase.setCreditCardNumber(originalTransaction.getCreditCardNumber());
        transactionBase.setCreditCardType(originalTransaction.getCreditCardType());

        transactionBaseRepository.save(transactionBase);

    }


    // credit card validation logic goes here. // For now only null check.
    private boolean isCreditCardNumberValid(String creditCardNumber) {
        if (creditCardNumber != null) {
            return true;
        }

        return false;
    }

}
