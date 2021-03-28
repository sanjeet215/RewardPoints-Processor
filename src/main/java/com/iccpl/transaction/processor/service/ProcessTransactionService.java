package com.iccpl.transaction.processor.service;

import com.iccpl.transaction.processor.dto.OriginalTransaction;
import org.springframework.stereotype.Service;

@Service
public interface ProcessTransactionService {

    public void processTransactionEvaluateAndStore(OriginalTransaction originalTransaction);
}
