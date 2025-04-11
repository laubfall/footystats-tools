package de.footystats.tools.services.bet;

import de.footystats.tools.services.bet.attributes.BaseBetAttribute;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BetTicketRepository extends MongoRepository<BetTicket, String> {
	List<BetTicket> findAllByMatchDocumentIdAndEvaluatedIsFalse(ObjectId matchDocumentId);


	@Deprecated //Does not work anymore
	@Query("{ 'chosenAttributeValues': {$all : ?0, $size: ?#{[0].size()} }}")
	List<BetTicket> findAllByChosenAttributeValues(List<BaseBetAttribute<?>> attributeIds);

}
