package com.funfair.api.tickettypedetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.common.Util;
import com.funfair.api.event.EventDetails;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.organizer.OrganizerDetails;

@Service
public class TicketTypeService {

	@Autowired
	TicketTypeDetailsRepository ticketTypeDetailsRepository;
	@Autowired
	Util util;

	public List<TicketTypeAddDetailsDto> getAllTicketTypes() {
		List<TicketTypeDetails> ticketTypeDetailsList = ticketTypeDetailsRepository.findAll();
		List<TicketTypeAddDetailsDto> dtos = new ArrayList<TicketTypeAddDetailsDto>();

		for (TicketTypeDetails ticketTypeDetails : ticketTypeDetailsList) {
			TicketTypeAddDetailsDto dto = convertTicketTypeDetailsToDto(ticketTypeDetails);
			dtos.add(dto);
		}
		return dtos;
	}

	public TicketTypeAddDetailsDto convertTicketTypeDetailsToDto(TicketTypeDetails ticketTypeDetails) {
		TicketTypeAddDetailsDto dto = new TicketTypeAddDetailsDto();
		dto.setEventId(ticketTypeDetails.getEventId());
		dto.setOrgId(ticketTypeDetails.getOrgId());
		dto.setQuntityAvialable(ticketTypeDetails.getQuntityAvialable());
		dto.setTicketDec(ticketTypeDetails.getTicketDec());
		dto.setTicketName(ticketTypeDetails.getTicketName());
		dto.setTickettypeId(ticketTypeDetails.getTicketTypeId());
		return dto;
	}

	public void saveTicketTypes(List<AddTicketTypeDetailsDto> ticketDtos, EventDetails event,
			OrganizerDetails organizer) {

		if (ticketDtos == null || ticketDtos.isEmpty())
			return;

		for (AddTicketTypeDetailsDto dto : ticketDtos) {
			TicketTypeDetails ticket = new TicketTypeDetails();
			ticket.setTicketTypeId(util.generateRandomNumericId());
			ticket.setTicketName(dto.getTicketName());
			ticket.setQuntityAvialable(dto.getQuntityAvialable());
			ticket.setTicketDec(dto.getTicketDec());
			ticket.setTicketPrice(dto.getTicketPrice());
			ticket.setEventId(event.getEventId());
			ticket.setOrgId(organizer.getOrgId());
			ticket.setCreatedBy(organizer.getOrgUserId());
			ticket.setCreatedOn(LocalDateTime.now());
			ticketTypeDetailsRepository.save(ticket);
		}
	}

	public List<GetTicketDetailsDto> getTicketsByEventId(String eventId) {
		List<TicketTypeDetails> ticketTypeDetailsList = ticketTypeDetailsRepository.findByEventId(eventId);
		List<GetTicketDetailsDto> dtos = new ArrayList<GetTicketDetailsDto>();
		for (TicketTypeDetails ticketTypeDetails : ticketTypeDetailsList) {
			GetTicketDetailsDto dto = new GetTicketDetailsDto();
			dto.setTicketTypeId(ticketTypeDetails.getTicketTypeId());
			dto.setEventId(ticketTypeDetails.getEventId());
			dto.setTicketPrice(ticketTypeDetails.getTicketPrice());
			dto.setOrgId(ticketTypeDetails.getOrgId());
			dto.setQuntityAvialable(ticketTypeDetails.getQuntityAvialable());
			dto.setTicketName(ticketTypeDetails.getTicketName());
			dto.setTicketDec(ticketTypeDetails.getTicketDec());
			dtos.add(dto);
		}
		return dtos;
	}

	public List<TicketTypeAddDetailsDto> getTicketTypesByEventId(String eventId) {
		List<TicketTypeDetails> ticketTypeDetailsList = ticketTypeDetailsRepository.findByEventId(eventId);
		List<TicketTypeAddDetailsDto> dtos = new ArrayList<TicketTypeAddDetailsDto>();
		for (TicketTypeDetails ticketTypeDetails : ticketTypeDetailsList) {
			TicketTypeAddDetailsDto dto = convertTicketTypeDetailsToDto(ticketTypeDetails);
			dtos.add(dto);
		}
		return dtos;
	}

	public TicketTypeAddDetailsDto getTicketTypeById(String tickettypeId) {
		TicketTypeDetails ticketTypeDetails = ticketTypeDetailsRepository.findByTicketTypeId(tickettypeId);
		if (ticketTypeDetails == null) {
			throw new BadRequestException("Ticket type not found for id: " + tickettypeId);
		}
		TicketTypeAddDetailsDto dto = convertTicketTypeDetailsToDto(ticketTypeDetails);
		return dto;
	}

	public double getLowestTicketPriceByEventId(String eventId) {
		System.out.println("Fetching lowest ticket price for event ID: " + eventId);

	    List<TicketTypeDetails> tickets =
	            ticketTypeDetailsRepository.findByEventIdAndIsActiveTrue(eventId);

	    if (tickets == null || tickets.isEmpty()) {
	        return 0.0;
	    }

	    double lowestPrice = tickets.get(0).getTicketPrice();

	    for (TicketTypeDetails ticket : tickets) {
	        if (ticket.getTicketPrice() < lowestPrice) {
	            lowestPrice = ticket.getTicketPrice();
	        }
	    }

	    return lowestPrice;
	}


}
