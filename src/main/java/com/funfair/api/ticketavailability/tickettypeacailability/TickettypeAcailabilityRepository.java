package com.funfair.api.ticketavailability.tickettypeacailability;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TickettypeAcailabilityRepository extends JpaRepository<TicketTypeAcailabilityDetails, Integer> {

	List<TicketTypeAcailabilityDetails> findByEventId(String eventId);

}
