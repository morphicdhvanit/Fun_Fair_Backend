package com.funfair.api.event.salespersonticketdetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.exception.BadRequestException;

@Service
public class SalespersonTicketService {

	@Autowired
	SalespersonTicketRepository salespersonTicketRepository;

	public SalespersonTicketDetails addSalespersonTicket(AddSalesPersonTicketDto dto) {

		// Basic validations
		if (dto.getEventId() == null || dto.getEventId().isEmpty()) {
			throw new BadRequestException("eventId is required");
		}
		if (dto.getSalespersonId() == null || dto.getSalespersonId().isEmpty()) {
			throw new BadRequestException("salespersonId is required");
		}
		if (dto.getTicketTypeId() == null || dto.getTicketTypeId().isEmpty()) {
			throw new BadRequestException("ticketTypeId is required");
		}
		if (dto.getTotalTicketsQuantity() <= 0) {
			throw new BadRequestException("totalTicketsQuantity must be greater than 0");
		}

		// Convert DTO → Entity
		SalespersonTicketDetails entity = convertToEntity(dto);

		// Save
		return salespersonTicketRepository.save(entity);
	}

	public SalespersonTicketDetails convertToEntity(AddSalesPersonTicketDto dto) {

		SalespersonTicketDetails entity = new SalespersonTicketDetails();

		entity.setEventId(dto.getEventId());
		entity.setSalespersonId(dto.getSalespersonId());
		entity.setTicketTypeId(dto.getTicketTypeId());
		entity.setTotalTicketsQuantity(dto.getTotalTicketsQuantity());
		entity.setTicketsSoldQuantity(0);
		entity.setTicketsAvailableQuantity(dto.getTotalTicketsQuantity());
		entity.setCreatedOn(LocalDateTime.now());

		return entity;
	}

	public List<SalesPersonTicketDetailsDto> getSalesPersonTicketDetails(String eventId, String salespersonId) {
		List<SalespersonTicketDetails> details = salespersonTicketRepository.findByEventIdAndSalespersonId(eventId,salespersonId);
		List<SalesPersonTicketDetailsDto> dto = new ArrayList<>();
		for (SalespersonTicketDetails salesPersonTicketDetailsDto : details) {
			SalesPersonTicketDetailsDto detailDto = new SalesPersonTicketDetailsDto();
			detailDto.setEventId(salesPersonTicketDetailsDto.getEventId());
			detailDto.setSalespersonId(salesPersonTicketDetailsDto.getSalespersonId());
			detailDto.setTicketTypeId(salesPersonTicketDetailsDto.getTicketTypeId());
			detailDto.setTotalTicketsQuantity(salesPersonTicketDetailsDto.getTotalTicketsQuantity());
			detailDto.setTicketsSoldQuantity(salesPersonTicketDetailsDto.getTicketsSoldQuantity());
			detailDto.setCreatedBy(salesPersonTicketDetailsDto.getCreatedBy());
			detailDto.setTicketsAvailableQuantity(salesPersonTicketDetailsDto.getTicketsAvailableQuantity());
			dto.add(detailDto);
		}
		return dto;
	}

	public List<SalesPersonTicketDetailsDto> getSalesPersonTicketDetailsByorgIdAndEventId(String eventId) {
		List<SalespersonTicketDetails> salespersonTicketDetails = salespersonTicketRepository.findByEventId(eventId);
		List<SalesPersonTicketDetailsDto> dtos = new ArrayList<>();
		for (SalespersonTicketDetails salespersonTicketDetail : salespersonTicketDetails) {
			SalesPersonTicketDetailsDto dto  = new SalesPersonTicketDetailsDto();
			dto.setEventId(salespersonTicketDetail.getEventId());
			dto.setSalespersonId(salespersonTicketDetail.getSalespersonId());
			dto.setTicketTypeId(salespersonTicketDetail.getTicketTypeId());
			dto.setTotalTicketsQuantity(salespersonTicketDetail.getTotalTicketsQuantity());
			dto.setTicketsSoldQuantity(salespersonTicketDetail.getTicketsSoldQuantity());
			dto.setTicketsAvailableQuantity(salespersonTicketDetail.getTicketsAvailableQuantity());
			dto.setCreatedBy(salespersonTicketDetail.getCreatedBy());
			dtos.add(dto);
			
		}
		return dtos;
	}

}
