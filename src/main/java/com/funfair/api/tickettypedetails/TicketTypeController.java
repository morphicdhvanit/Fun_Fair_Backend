package com.funfair.api.tickettypedetails;

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
@RequestMapping("/ticket/type")
@CrossOrigin("*")
public class TicketTypeController {
	
	@Autowired
	TicketTypeService ticketTypeService;
	
	@GetMapping("/")
	public ResponseEntity<List<TicketTypeAddDetailsDto>> getAllTicketTypes(){
		List<TicketTypeAddDetailsDto> response = ticketTypeService.getAllTicketTypes();
		return new ResponseEntity<List<TicketTypeAddDetailsDto>>(response,  HttpStatus.OK);
	}
	@GetMapping("/by-event/{eventId}")
	public ResponseEntity<List<TicketTypeAddDetailsDto>> getTicketTypesByEventId(@PathVariable String eventId) {
		List<TicketTypeAddDetailsDto> response = ticketTypeService.getTicketTypesByEventId(eventId);
		return new ResponseEntity<List<TicketTypeAddDetailsDto>>(response, HttpStatus.OK);
	}
	@GetMapping("/{tickettypeId}")
	public ResponseEntity<TicketTypeAddDetailsDto> getTicketTypeById(@PathVariable String tickettypeId) {
		TicketTypeAddDetailsDto response = ticketTypeService.getTicketTypeById(tickettypeId);
		return new ResponseEntity<TicketTypeAddDetailsDto>(response, HttpStatus.OK);
	}
	
	

}
