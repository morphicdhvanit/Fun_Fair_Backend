package com.funfair.api.tickettypedetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeDetailsRepository extends JpaRepository<TicketTypeDetails, String> {

	List<TicketTypeDetails> findByEventId(String eventId);

	TicketTypeDetails findByTicketTypeId(String ticketTypeId);

	List<TicketTypeDetails> findByEventIdAndIsActiveTrue(String eventId);

}
