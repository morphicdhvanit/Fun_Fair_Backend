package com.funfair.api.bookingticketdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking/ticket-details")
public class BookingTicketController {
	
	@Autowired
	BookingTicketService bookingTickeService;

	
	@GetMapping("/{ticketNumber}/qr")
	public ResponseEntity<byte[]> getTicketQr(@PathVariable String ticketNumber) {

		byte[] qr = bookingTickeService.getTicketQrCode(ticketNumber);

		return ResponseEntity.ok().header("Content-Type", "image/png").body(qr);

	}
}
