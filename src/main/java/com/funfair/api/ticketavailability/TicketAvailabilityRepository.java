package com.funfair.api.ticketavailability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketAvailabilityRepository extends JpaRepository<TicketAvailabilityDetails, Integer>{

	TicketAvailabilityDetails findByEventId(String eventId);

}
