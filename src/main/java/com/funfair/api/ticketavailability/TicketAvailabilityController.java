package com.funfair.api.ticketavailability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfair.api.tickettypedetails.TicketTypeAddDetailsDto;

import lombok.Data;

@RestController
@RequestMapping("/api/ticket-availability")
@CrossOrigin("*")
public class TicketAvailabilityController {
	
	@Autowired
	TicketAvailabilityService ticketAvailabilityService;
	

	@GetMapping("/check-avilable-tickets/{eventId}")
	public ResponseEntity<TicketAvilableDetailsDto> checkAvailableTickets(@PathVariable String eventId) {
		TicketAvilableDetailsDto response = ticketAvailabilityService.checkAvailableTickets(eventId);
		return new ResponseEntity<TicketAvilableDetailsDto>(response , HttpStatus.OK);
	}
}
