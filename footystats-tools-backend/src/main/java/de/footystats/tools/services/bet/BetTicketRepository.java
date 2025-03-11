package de.footystats.tools.services.bet;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BetTicketRepository extends MongoRepository<BetTicket, String> {
	List<BetTicket> findAllByMatchDocumentIdAndEvaluatedIsFalse(ObjectId matchDocumentId);

	@Query("{ 'attributeIds': {$all : ?0, $size: ?#{[0].size()} }}")
	List<BetTicket> findAllByAttributeIds(List<ObjectId> attributeIds);
	
}
