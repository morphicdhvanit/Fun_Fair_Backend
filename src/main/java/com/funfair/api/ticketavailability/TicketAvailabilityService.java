package com.funfair.api.ticketavailability;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.event.EventDetails;
import com.funfair.api.event.EventRepository;
import com.funfair.api.event.addeventdetailsdtos.AddTicketsAndPricingDetailsDto;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.ticketavailability.tickettypeacailability.AddTicketTypeAvilabilityDetailsDto;
import com.funfair.api.ticketavailability.tickettypeacailability.TicketTypeAvilabilityDetails;
import com.funfair.api.ticketavailability.tickettypeacailability.TickettypeAvilabilityRepository;
import com.funfair.api.tickettypedetails.AddTicketTypeDetailsDto;
import com.funfair.api.tickettypedetails.TicketTypeAddDetailsDto;
import com.funfair.api.tickettypedetails.TicketTypeDetails;

import jakarta.transaction.Transactional;

@Service
public class TicketAvailabilityService {

	@Autowired
	TicketAvailabilityRepository ticketAvailabilityRepository;
	@Autowired
	TickettypeAvilabilityRepository tickettypeAvilabilityRepository;
	@Autowired
	EventRepository eventRepository;

	public TicketAvilableDetailsDto checkAvailableTickets(String eventId) {

		TicketAvailabilityDetails ticketAvailabilityDetails = ticketAvailabilityRepository.findByEventId(eventId);

		if (ticketAvailabilityDetails == null) {
			throw new BadRequestException("Invalid event id");
		}

		EventDetails eventDetails = eventRepository.findByEventId(eventId);
		TicketAvilableDetailsDto response = new TicketAvilableDetailsDto();
		response.setEventname(eventDetails.getEventTitle());
		response.setEventStartDateTime(eventDetails.getEventStartDateTime());
		response.setEventEndDateTime(eventDetails.getEventEndDateTime());
		List<AvilableTicketDetailsDto> ticketTypeDetails = getTicketTypeDetails(eventId);
		response.setAvailableTicketDetails(ticketTypeDetails);
		return response;
	}

	private List<AvilableTicketDetailsDto> getTicketTypeDetails(String eventId) {
		List<TicketTypeAvilabilityDetails> ticketTypeDetails = tickettypeAvilabilityRepository.findByEventId(eventId);
		List<AvilableTicketDetailsDto> dtos = new ArrayList<>();
		for (TicketTypeAvilabilityDetails TicketTypeAcailabilityDetails : ticketTypeDetails) {
			AvilableTicketDetailsDto dto = new AvilableTicketDetailsDto();
			dto.setQuntityAvialable(TicketTypeAcailabilityDetails.getTotalAvailableTicket()
					- TicketTypeAcailabilityDetails.getTotalFreezedTicket());
			dto.setTicketName(TicketTypeAcailabilityDetails.getTicketTypeName());
			dto.setTicketTypeId(TicketTypeAcailabilityDetails.getTicketTypeId());
			dto.setTicketPrice(TicketTypeAcailabilityDetails.getTicketPrice());
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional
	public void addTicketAvailabilityDetails(AddTicketAvilabilityDetailDto dto) {

		// SAVE EVENT LEVEL AVAILABILITY

		TicketAvailabilityDetails availability = new TicketAvailabilityDetails();

		availability.setEventId(dto.getEventId());
		availability.setOrgId(dto.getOrgId());

		availability.setTotalTickets(dto.getTotalTickets());

		availability.setAvailableTickets(dto.getTotalTickets());

		availability.setSoldTickets(0);

		availability.setFreezedTickets(0);

		availability.setBookingPrice(dto.getBookingPrice());

		availability.setCreatedBy(dto.getCreatedBy());
		availability.setCreatedOn(LocalDateTime.now());

		ticketAvailabilityRepository.save(availability);

		// SAVE TICKET TYPE AVAILABILITY

		for (AddTicketTypeAvilabilityDetailsDto typeDto : dto.getTicketTypeAvilabilityDetails()) {

			TicketTypeAvilabilityDetails type = new TicketTypeAvilabilityDetails();

			type.setEventId(dto.getEventId());

			type.setOrgId(dto.getOrgId());

			type.setTicketTypeId(typeDto.getTicketTypeId());

			type.setTicketTypeName(typeDto.getTicketName());

			type.setTicketPrice(typeDto.getTicketPrice());

			type.setTotalAvailableTicket(typeDto.getQuntityAvialable());

			type.setTotalSoldTicket(0);

			type.setTotalFreezedTicket(0);

			type.setCreatedBy(dto.getCreatedBy());

			type.setCreatedOn(LocalDateTime.now());

			tickettypeAvilabilityRepository.save(type);

		}

	}

	public CheckoutDetailsDto checkoutDetails(String eventId, String ticketTypeId, int quantity) {

	    // Validate quantity
	    if (quantity <= 0) {
	        throw new BadRequestException("Invalid ticket quantity");
	    }

	    // Fetch ticket type details
	    TicketTypeAvilabilityDetails ticketTypeDetails =
	            tickettypeAvilabilityRepository.findByEventIdAndTicketTypeId(eventId, ticketTypeId);

	    if (ticketTypeDetails == null) {
	        throw new BadRequestException("Ticket type not found");
	    }

	    // ✅ Availability check
	    if (quantity > ticketTypeDetails.getTotalAvailableTicket()) {

	        throw new RuntimeException(
	                "Requested quantity not available. Only "
	                        + ticketTypeDetails.getTotalAvailableTicket()
	                        + " tickets left."
	        );
	    }

	    // Fetch booking fee
	    TicketAvailabilityDetails availabilityDetails =
	            ticketAvailabilityRepository.findByEventId(eventId);

	    if (availabilityDetails == null) {
	        throw new RuntimeException("Ticket availability not found");
	    }

	    double oneTicketPrice = ticketTypeDetails.getTicketPrice();

	    // subtotal
	    double subTotal = oneTicketPrice * quantity;

	    // booking fee (per ticket × quantity)
	    double bookingFee = availabilityDetails.getBookingPrice();

	    double discount = 0;

	    // total amount
	    double totalAmount = subTotal + bookingFee - discount;

	    // Prepare DTO
	    CheckoutDetailsDto dto = new CheckoutDetailsDto();
	    dto.setEventId(eventId);
	    dto.setTicketTypeId(ticketTypeId);
	    dto.setQuantity(quantity);
	    dto.setOneticketPrice(oneTicketPrice);
	    dto.setSubTotal(subTotal);
	    dto.setBookingFee(bookingFee);
	    dto.setDiscount(discount);
	    dto.setTotalAmount(totalAmount);

	    return dto;
	}



}
