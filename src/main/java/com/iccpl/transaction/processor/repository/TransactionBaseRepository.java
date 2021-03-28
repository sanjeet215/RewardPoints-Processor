package com.iccpl.transaction.processor.repository;

import com.iccpl.transaction.processor.domain.TransactionBase;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBaseRepository extends MongoRepository<TransactionBase, String> {

    Optional<List<TransactionBase>> findByCreditCardNumberAndTransactionTimeStampBetween(String creditCardNumber, LocalDateTime startTime, LocalDateTime endTime);
}
