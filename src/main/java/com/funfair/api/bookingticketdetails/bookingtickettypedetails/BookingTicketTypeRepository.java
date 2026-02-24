package com.funfair.api.bookingticketdetails.bookingtickettypedetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTicketTypeRepository   extends JpaRepository<BookingTicketTypeDetails, Integer>{

	List<BookingTicketTypeDetails> findByTicketNumber(String ticketNumber);

}
