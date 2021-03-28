package com.iccpl.transaction.processor.service;

import com.iccpl.transaction.processor.dto.PointBreakDownByMonth;
import com.iccpl.transaction.processor.dto.RewardPointsByMonthResponse;
import com.iccpl.transaction.processor.dto.RewardPointsRequest;
import com.iccpl.transaction.processor.dto.RewardPointsResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface RewardPointsService {

    RewardPointsResponse findRewardPointsByDateRange(RewardPointsRequest rewardPointsRequest);

    RewardPointsResponse findRewardPointsByTransactionType(String transactionType);

    List<RewardPointsByMonthResponse> findRewardPointsGroupByMonth(PointBreakDownByMonth pointBreakDownByMonth);
}
