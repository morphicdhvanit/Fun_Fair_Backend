package com.funfair.api.ticketavailability.tickettypeacailability;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TickettypeAvilabilityRepository extends JpaRepository<TicketTypeAvilabilityDetails, Integer> {

	List<TicketTypeAvilabilityDetails> findByEventId(String eventId);

	List<TicketTypeAvilabilityDetails> findByEventIdAndIsActiveTrue(String eventId);

	TicketTypeAvilabilityDetails findByEventIdAndTicketTypeId(String eventId, String ticketTypeId);

}
