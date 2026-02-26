package com.funfair.api.bookingticketdetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.bookingticketdetails.bookingtickettypedetails.BookingTicketTypeDetails;
import com.funfair.api.bookingticketdetails.bookingtickettypedetails.BookingTicketTypeDetailsDto;
import com.funfair.api.bookingticketdetails.bookingtickettypedetails.BookingTicketTypeRepository;
import com.funfair.api.common.QrCodeService;
import com.funfair.api.event.salespersonticketdetails.SalespersonTicketDetails;
import com.funfair.api.event.salespersonticketdetails.SalespersonTicketRepository;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.salespersonticketbookingdetails.PaymnetMethodTypeEnum;
import com.funfair.api.ticketavailability.TicketAvailabilityDetails;
import com.funfair.api.ticketavailability.TicketAvailabilityRepository;

@Service
public class BookingTicketService {
	@Autowired
	BookingTicketRepository bookingTicketRepository;

	@Autowired
	BookingTicketTypeRepository bookingTicketTypeRepository;
	@Autowired
	SalespersonTicketRepository salespersonTicketRepository;

	@Autowired
	TicketAvailabilityRepository ticketAvailabilityRepository;

	@Autowired
	QrCodeService qrCodeService;

	// =========================================================
	// MAIN METHOD — SAVE TICKET
	// =========================================================

	public BookingTicketDetails createBookingTicket(BookTicketDetailsDto dto) {

		BookingTicketDetails ticket = new BookingTicketDetails();

		ticket.setTicketNumber(generateTicketNumber());

		ticket.setTicketQrToken(generateTicketToken());

		ticket.setSalespersonId(dto.getSalespersonId());

		ticket.setEventId(dto.getEventId());

		ticket.setOrganizerId(dto.getOrganizerId());

		ticket.setPaymentMethod(getPaymentMethod(dto.getPaymentMethod()));

		ticket.setTicketAssignBySalesPerson(dto.isTicketAssignBySalesPerson());

		ticket.setCreatedBy(dto.getCreatedBy());

		ticket.setCreatedOn(LocalDateTime.now());

		// ================================
		// CALCULATE TOTAL HERE
		// ================================

		double finalAmount = 0;
		int totalQuantity = 0;

		for (BookingTicketTypeDetailsDto typeDto : dto.getBookTicketDetailsDtos()) {

			double typeTotal = typeDto.getTicketQuantity() * typeDto.getOneTicketPrice();

			finalAmount += typeTotal;

			totalQuantity += typeDto.getTicketQuantity();
		}

		ticket.setTotalPaymentAmount(finalAmount);

		ticket.setTotalquantity(totalQuantity);

		// save main ticket
		BookingTicketDetails savedTicket = bookingTicketRepository.save(ticket);

		// save type details
		saveTicketTypeDetails(dto, savedTicket.getTicketNumber());

		return savedTicket;
	}

	public void saveTicketTypeDetails(BookTicketDetailsDto dto, String ticketNumber) {

		for (BookingTicketTypeDetailsDto typeDto : dto.getBookTicketDetailsDtos()) {

			int quantity = typeDto.getTicketQuantity();

			// =====================================================
			// SALESPERSON BOOKING
			// =====================================================
			System.out.println("dto.isTicketAssignBySalesPerson"+dto.isTicketAssignBySalesPerson());

			if (dto.isTicketAssignBySalesPerson()) {

				SalespersonTicketDetails sp = salespersonTicketRepository.findByEventIdAndSalespersonIdAndTicketTypeId(dto.getEventId(), dto.getSalespersonId(), typeDto.getTicketTypeId());

				if (sp == null)
					throw new BadRequestException("Salesperson ticket not found");

				if (sp.getTicketsAvailableQuantity() < quantity)
					throw new BadRequestException("Not enough tickets available for salesperson");

				sp.setTicketsAvailableQuantity(sp.getTicketsAvailableQuantity() - quantity);

				sp.setTicketsSoldQuantity(sp.getTicketsSoldQuantity() + quantity);

				sp.setUpdatedBy(dto.getCreatedBy());
				sp.setUpdatedOn(LocalDateTime.now());

				salespersonTicketRepository.save(sp);

			}

			// =====================================================
			// CUSTOMER BOOKING
			// =====================================================

			else {

				TicketAvailabilityDetails availability = ticketAvailabilityRepository.findByEventIdAndOrgId(dto.getEventId(), dto.getOrganizerId());

				if (availability == null)
					throw new BadRequestException("Ticket availability not found");

				if (availability.getAvailableTickets() < quantity)
					throw new BadRequestException("Not enough tickets available");

				availability.setAvailableTickets(availability.getAvailableTickets() - quantity);

				availability.setSoldTickets(availability.getSoldTickets() + quantity);

				availability.setUpdatedBy(dto.getCreatedBy());
				availability.setUpdatedOn(LocalDateTime.now());

				ticketAvailabilityRepository.save(availability);

			}

			// =====================================================
			// SAVE BOOKING TYPE DETAILS
			// =====================================================

			BookingTicketTypeDetails type = new BookingTicketTypeDetails();

			type.setTicketNumber(ticketNumber);

			type.setEventId(dto.getEventId());

			type.setOrganizerId(dto.getOrganizerId());

			type.setTicketTypeId(typeDto.getTicketTypeId());

			type.setTicketQuantity(quantity);

			type.setOneTicketPrice(typeDto.getOneTicketPrice());

			type.setTotalTicketPrice(quantity * typeDto.getOneTicketPrice());

			type.setCreatedBy(dto.getCreatedBy());

			type.setCreatedOn(LocalDateTime.now());

			bookingTicketTypeRepository.save(type);

		}

	}

	// =========================================================
	// GENERATE QR
	// =========================================================

	public byte[] getTicketQrCode(String ticketNumber) {

		BookingTicketDetails ticket = bookingTicketRepository.findByTicketNumber(ticketNumber);

		if (ticket == null)
			throw new BadRequestException("Ticket not found");

		return qrCodeService.generateQrCode(ticket.getTicketQrToken());

	}

	// =========================================================
	// TOKEN GENERATE
	// =========================================================

	private String generateTicketToken() {

		return UUID.randomUUID().toString();

	}

	// =========================================================
	// TICKET NUMBER GENERATE
	// =========================================================

	private String generateTicketNumber() {

		String prefix = "FF";

		String year = String.valueOf(LocalDate.now().getYear());

		Long count = bookingTicketRepository.count() + 1;

		return prefix + "-" + year + "-" + String.format("%06d", count);

	}

	// =========================================================
	// PAYMENT METHOD
	// =========================================================

	private PaymnetMethodTypeEnum getPaymentMethod(String payment) {

		if (payment.equalsIgnoreCase("CARD"))
			return PaymnetMethodTypeEnum.CARD;

		if (payment.equalsIgnoreCase("CASH"))
			return PaymnetMethodTypeEnum.CASH;

		if (payment.equalsIgnoreCase("UPI"))
			return PaymnetMethodTypeEnum.UPIPAYMNET;

		throw new BadRequestException("Invalid Payment Method");

	}
	
	public BookingTicketDetails getByTicketNumber(String ticketNumber) {
		BookingTicketDetails bookingTicketDetails = bookingTicketRepository.findByTicketNumber(ticketNumber);
		return bookingTicketDetails;
	}
	
	public List<BookingTicketTypeDetails> GetTicketTypeDetailsByTicketNumber(String ticketNumber) {
		List<BookingTicketTypeDetails> bookingTicketTypeDetails = bookingTicketTypeRepository.findByTicketNumber(ticketNumber);
		return bookingTicketTypeDetails;
 		
	}

}