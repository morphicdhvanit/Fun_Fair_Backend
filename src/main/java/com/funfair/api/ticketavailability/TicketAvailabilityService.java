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
import com.funfair.api.ticketavailability.tickettypeacailability.TicketTypeAcailabilityDetails;
import com.funfair.api.ticketavailability.tickettypeacailability.TickettypeAcailabilityRepository;
import com.funfair.api.tickettypedetails.AddTicketTypeDetailsDto;
import com.funfair.api.tickettypedetails.TicketTypeAddDetailsDto;
import com.funfair.api.tickettypedetails.TicketTypeDetails;

import jakarta.transaction.Transactional;

@Service
public class TicketAvailabilityService {

	@Autowired
	TicketAvailabilityRepository ticketAvailabilityRepository;
	@Autowired
	TickettypeAcailabilityRepository tickettypeAcailabilityRepository;
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
		List<TicketTypeAcailabilityDetails> ticketTypeDetails = tickettypeAcailabilityRepository.findByEventId(eventId);
		List<AvilableTicketDetailsDto> dtos = new ArrayList<>();
		for (TicketTypeAcailabilityDetails TicketTypeAcailabilityDetails : ticketTypeDetails) {
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

			TicketTypeAcailabilityDetails type = new TicketTypeAcailabilityDetails();

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

			tickettypeAcailabilityRepository.save(type);

		}

	}

}
