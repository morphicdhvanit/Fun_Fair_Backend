package com.funfair.api.event.salespersonticketdetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/event/salesperson-ticket")
@CrossOrigin("*")
public class SalesPersonTicketController {
	
	@Autowired
	SalespersonTicketService salespersonTicketService;
	
	
	@GetMapping("/by-orgId/{eventId}")
	public ResponseEntity<List<SalesPersonTicketDetailsDto>> getSalesPersonTicketDetailsByorgIdAndEventId( @PathVariable String eventId) {
		List<SalesPersonTicketDetailsDto> response  = salespersonTicketService.getSalesPersonTicketDetailsByorgIdAndEventId(eventId);
		return new ResponseEntity<List<SalesPersonTicketDetailsDto>>(response, HttpStatus.OK);
	}
	
	

}
