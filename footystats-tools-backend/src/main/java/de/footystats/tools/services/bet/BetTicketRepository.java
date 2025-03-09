package de.footystats.tools.services.bet;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BetTicketRepository extends MongoRepository<BetTicket, String> {
	List<BetTicket> findAllByMatchDocumentIdAndEvaluatedIsFalse(ObjectId matchDocumentId);
}
