package com.funfair.api.salespersonticketdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalespersonTicketBookingRepository extends JpaRepository<SalespersonTicketBookingDetails, Integer> {

	SalespersonTicketBookingDetails findByTicketNumber(String ticketNumber);

}
