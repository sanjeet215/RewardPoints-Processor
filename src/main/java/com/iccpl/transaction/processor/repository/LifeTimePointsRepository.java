package com.iccpl.transaction.processor.repository;

import com.iccpl.transaction.processor.domain.LifeTImePoints;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeTimePointsRepository extends MongoRepository<LifeTImePoints, String> {

    Optional<LifeTImePoints> findByCreditCardNumber(String creditCardNumber);
}

