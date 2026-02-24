package com.funfair.api.salespersonticketbookingdetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalespersonTicketBookingRepository extends JpaRepository<SalespersonTicketBookingDetails, Integer> {

	SalespersonTicketBookingDetails findByTicketNumber(String ticketNumber);

	List<SalespersonTicketBookingDetails> findByEventIdAndSalespersonIdAndIsActiveTrue(String eventId,
			String salespersonId);

}
