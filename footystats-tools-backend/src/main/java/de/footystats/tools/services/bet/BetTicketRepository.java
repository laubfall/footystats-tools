package de.footystats.tools.services.bet;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetTicketRepository extends MongoRepository<BetTicket, String> {
}
