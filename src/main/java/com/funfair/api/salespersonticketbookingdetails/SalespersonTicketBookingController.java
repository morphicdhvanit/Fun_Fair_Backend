package com.funfair.api.salespersonticketbookingdetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.funfair.api.bookingticketdetails.BookTicketDetailsDto;

@RestController
@RequestMapping("/salesperson-ticket-booking")
@CrossOrigin("*")
public class SalespersonTicketBookingController {

	@Autowired
	SalespersonTicketBookingService salespersonTicketBookingService;

	@PostMapping("/book-ticket")
	public ResponseEntity<SalespersonTicketBookingDetails> bookTicket(@RequestBody BookTicketDetailsDto bookTicketDetailsDto) {
		SalespersonTicketBookingDetails bookingDetails = salespersonTicketBookingService.bookTicket(bookTicketDetailsDto);
		return new ResponseEntity<SalespersonTicketBookingDetails>(bookingDetails, HttpStatus.OK);
	}
	@GetMapping("/by-eventId/{eventId}")
	public ResponseEntity<List<SalesPersonSoldTicketDetailsDto>> byEventIdAndSalesPersonId(@PathVariable String eventId , @RequestParam String SalespersonId){
		List<SalesPersonSoldTicketDetailsDto> response = salespersonTicketBookingService.byEventIdAndSalesPersonId(eventId,SalespersonId);
		return new ResponseEntity<List<SalesPersonSoldTicketDetailsDto>>(response, HttpStatus.OK);
	}

}
