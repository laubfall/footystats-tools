package de.footystats.tools.services.bet;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetAttributeRepository extends MongoRepository<BaseBetAttribute<?>, ObjectId> {
}
