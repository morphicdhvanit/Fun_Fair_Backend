package com.funfair.api.bookingticketdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTicketRepository extends JpaRepository<BookingTicketDetails, Integer>{

	BookingTicketDetails findByTicketNumber(String ticketNumber);

}
