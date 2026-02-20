package com.funfair.api.salespersonticketdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salesperson-ticket-booking")
@CrossOrigin("*")
public class SalespersonTicketBookingController {

	@Autowired
	SalespersonTicketBookingService salespersonTicketBookingService;

	@PostMapping("/book-ticket")
	public ResponseEntity<SalespersonTicketBookingDetails> bookTicket(
			@RequestBody BookTicketDetailsDto bookTicketDetailsDto) {
		SalespersonTicketBookingDetails bookingDetails = salespersonTicketBookingService
				.bookTicket(bookTicketDetailsDto);
		return new ResponseEntity<SalespersonTicketBookingDetails>(bookingDetails, HttpStatus.OK);
	}

	@GetMapping("/{ticketNumber}/qr")
	public ResponseEntity<byte[]> getTicketQr(@PathVariable String ticketNumber) {

		byte[] qr = salespersonTicketBookingService.getTicketQrCode(ticketNumber);

		return ResponseEntity.ok().header("Content-Type", "image/png").body(qr);

	}

}
