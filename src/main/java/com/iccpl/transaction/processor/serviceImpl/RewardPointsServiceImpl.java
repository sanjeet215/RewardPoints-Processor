package com.iccpl.transaction.processor.serviceImpl;

import com.iccpl.transaction.processor.domain.LifeTImePoints;
import com.iccpl.transaction.processor.domain.TransactionBase;
import com.iccpl.transaction.processor.dto.*;
import com.iccpl.transaction.processor.exception.InternalServerError;
import com.iccpl.transaction.processor.exception.ResourceNotFoundException;
import com.iccpl.transaction.processor.repository.LifeTimePointsRepository;
import com.iccpl.transaction.processor.repository.TransactionBaseRepository;
import com.iccpl.transaction.processor.service.RewardPointsService;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RewardPointsServiceImpl implements RewardPointsService {

    @Autowired
    LifeTimePointsRepository lifeTimePointsRepository;

    @Autowired
    TransactionBaseRepository transactionBaseRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public RewardPointsResponse findRewardPointsByDateRange(RewardPointsRequest rewardPointsRequest) {

        try {
            RewardPointsResponse rewardPointsResponse = new RewardPointsResponse();
            Optional<List<TransactionBase>> transactionBaseList =
                    transactionBaseRepository.findByCreditCardNumberAndTransactionTimeStampBetween(rewardPointsRequest.getCreditCardNumber(), rewardPointsRequest.getStartTime(), rewardPointsRequest.getEndTime());
            transactionBaseList.ifPresent(transactionBases -> generateRewardPointsResponse(transactionBases.get(0), rewardPointsResponse));
            transactionBaseList.ifPresent(transactionBases -> rewardPointsResponse.setRewardPoints(generateRewardPointsBreakResponse(transactionBases)));

            return rewardPointsResponse;
        } catch (Exception exception) {
            throw new InternalServerError("exception while getting reward points.");
        }

    }

    private void generateRewardPointsResponse(TransactionBase transactionBase, RewardPointsResponse rewardPointsResponse) {
        Optional<LifeTImePoints> lifeTImePoints = lifeTimePointsRepository.findByCreditCardNumber(transactionBase.getCreditCardNumber());

        int totalPoints = 0;

        if (lifeTImePoints.isPresent()) {
            totalPoints = lifeTImePoints.get().getLifeTimePoints();
        }

        if (lifeTImePoints.isPresent()) {
            rewardPointsResponse.setCreditCardNumber(transactionBase.getCreditCardNumber());
            rewardPointsResponse.setLifeTimePoints(totalPoints);
        }
    }


    private List<RewardPointsBreak> generateRewardPointsBreakResponse(List<TransactionBase> transactionList) {

        return transactionList.stream()
                .map(record -> new RewardPointsBreak(record.getTransactionNumber(), record.getRewardPoints(), record.getTransactionTimeStamp()))
                .collect(Collectors.toList());
    }


    @Override
    public RewardPointsResponse findRewardPointsByTransactionType(String transactionType) {
        return null;
    }

    @Override
    public List<RewardPointsByMonthResponse> findRewardPointsGroupByMonth(PointBreakDownByMonth pointBreakDownByMonth) {

        LocalDateTime startTime = LocalDateTime.now().minusYears(1);
        LocalDateTime endTime = LocalDateTime.now();

        Optional<List<TransactionBase>> transactionBaseList =
                transactionBaseRepository.findByCreditCardNumberAndTransactionTimeStampBetween(pointBreakDownByMonth.getCreditCardNumber()
                        , startTime,endTime);

        if(transactionBaseList.isPresent()) {
            return transactionBaseList.get()
                    .stream()
                    .map(transactionBase -> new RewardPointsByMonthResponse(transactionBase.getTransactionTimeStamp().getMonth().toString(),transactionBase.getRewardPoints()))
                    .collect(Collectors.groupingBy(RewardPointsByMonthResponse::getMonth,Collectors.summingInt(RewardPointsByMonthResponse::getPoints)))
                    .entrySet()
                    .stream()
                    .map(record -> new RewardPointsByMonthResponse(record.getKey(),record.getValue()))
                    .collect(Collectors.toList());
        }

        throw new ResourceNotFoundException("No transactions found");
    }
}
