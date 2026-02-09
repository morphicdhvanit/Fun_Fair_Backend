package com.funfair.api.event.salespersonticketdetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalespersonTicketRepository extends JpaRepository<SalespersonTicketDetails, Integer>{

	List<SalespersonTicketDetails> findByEventIdAndSalespersonId(String eventId, String salespersonId);

	List<SalespersonTicketDetails> findByEventId(String eventId);

}
