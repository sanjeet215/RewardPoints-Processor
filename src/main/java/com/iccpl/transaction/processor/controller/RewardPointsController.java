package com.iccpl.transaction.processor.controller;

import com.iccpl.transaction.processor.dto.*;
import com.iccpl.transaction.processor.service.RewardPointsService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
@Slf4j
public class RewardPointsController {

    @Autowired
    RewardPointsService rewardPointsService;

    @GetMapping("/getPointsByRange")
    @ResponseStatus(HttpStatus.OK)
    public RewardPointsResponse getPointsBreakDownByDates(@Validated @RequestBody RewardPointsRequest rewardPointsRequest) {
        return rewardPointsService.findRewardPointsByDateRange(rewardPointsRequest);
    }

    @GetMapping("/getPointsByMonth")
    @ResponseStatus(HttpStatus.OK)
    public List<RewardPointsByMonthResponse> getPointsBreakDownByMonth(@Validated @RequestBody PointBreakDownByMonth pointBreakDownByMonth) {
        return rewardPointsService.findRewardPointsGroupByMonth(pointBreakDownByMonth);
    }

}

