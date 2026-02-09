package com.funfair.api.event.addeventdetailsdtos;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.common.Util;
import com.funfair.api.event.EventAgeGroupEnum;
import com.funfair.api.event.EventCatagoryEnum;
import com.funfair.api.event.EventDetails;
import com.funfair.api.event.EventRepository;
import com.funfair.api.event.EventTicketNeededForEnum;
import com.funfair.api.event.EventTypeEnum;
import com.funfair.api.event.TicketTypeEnum;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.organizer.OrganizerDetails;
import com.funfair.api.organizer.OrganizerRepository;
import com.funfair.api.tickettypedetails.TicketTypeService;

@Service
public class ConvertDtoToEntityService {

	@Autowired
	Util util;
	@Autowired
	EventRepository eventRepository;
	@Autowired
	TicketTypeService ticketTypeService;
	@Autowired
	OrganizerRepository organizerRepository;

	public EventDetails convertOrgDetailsDtoToEntity(AddOrgDetailsRequestDto addOrgDetailsRequestDto) {
		EventDetails details = new EventDetails();
		details.setEventId(util.generateRandomNumericId());
		details.setCreatedBy(addOrgDetailsRequestDto.getEventOrganizerName());
		details.setCreatedOn(LocalDateTime.now());
		details.setOrganizerId(addOrgDetailsRequestDto.getOrgId());
		details.setEventOrganizerContactNumber(addOrgDetailsRequestDto.getEventOrganizerContactNumber());
		details.setEventOrganizerEmailId(addOrgDetailsRequestDto.getEventOrganizerEmailId());
		details.setEventOrganizerName(addOrgDetailsRequestDto.getEventOrganizerName());
		return details;
	}

	public void convertBasicInfoDtoToEntity(AddBasicInfoRequestDto dto, EventDetails event) {

		event.setEventTitle(dto.getEventTitle());

		if (dto.getEventCatagory() != null) {
			event.setEventCatagory(getEventCategory(dto.getEventCatagory()));
		}

		event.setEventTags(dto.getEventTags());
		event.setEventShortDescription(dto.getEventShortDescription());

		if (dto.getEntryAllowFor() != null) {
			event.setEntryAllowFor(getEventAgeGroup(dto.getEntryAllowFor()));
		}

		if (dto.getTicketNeededFor() != null) {
			event.setTicketNeededFor(getTicketNeededFor(dto.getTicketNeededFor()));
		}

		event.setUpdatedOn(LocalDateTime.now());
	}

	private String getProperSlugByEventId(String eventId) {
		// 1️⃣ Fetch event details
		EventDetails event = eventRepository.findByEventId(eventId);
		if (event == null) {
			throw new BadRequestException("Event not found for ID: " + eventId);
		}
		// 2️⃣ First priority → Organizer info
		if (event.getEventOrganizerContactNumber() == null || event.getEventOrganizerContactNumber().trim().isEmpty()) {
			return "organizer-info";
		}
		// 3️⃣ Second priority → Basic event info
		if (event.getEventTitle() == null || event.getEventTitle().trim().isEmpty()) {

			return "basic-info";
		}

		if (event.getEventStartDateTime() == null || event.getEventStartDateTime().toString().trim().isEmpty()) {

			return "date-time";
		}

		return "";
	}

	private EventTypeEnum geteventType(String eventType) {
		EventTypeEnum type = null;
		if (eventType.equalsIgnoreCase(EventTypeEnum.ON_LOCATION.name()))
			type = EventTypeEnum.ON_LOCATION;
		if (eventType.equalsIgnoreCase(EventTypeEnum.PRIVATE.name()))
			type = EventTypeEnum.PRIVATE;
		if (type == null)
			throw new BadRequestException("Invalid Event type: " + type);
		return type;
	}

	private EventCatagoryEnum getEventCategory(String value) {
		EventCatagoryEnum category = null;

		if (value.equalsIgnoreCase(EventCatagoryEnum.MUSIC.getName()))
			category = EventCatagoryEnum.MUSIC;

		if (value.equalsIgnoreCase(EventCatagoryEnum.THEATER.getName()))
			category = EventCatagoryEnum.THEATER;

		if (value.equalsIgnoreCase(EventCatagoryEnum.COMEDY.getName()))
			category = EventCatagoryEnum.COMEDY;

		if (value.equalsIgnoreCase(EventCatagoryEnum.FAIRS.getName()))
			category = EventCatagoryEnum.FAIRS;

		if (value.equalsIgnoreCase(EventCatagoryEnum.FESTIVALS.getName()))
			category = EventCatagoryEnum.FESTIVALS;
		if (value.equalsIgnoreCase(EventCatagoryEnum.CULTURAL.getName()))
			category = EventCatagoryEnum.CULTURAL;

		if (value.equalsIgnoreCase(EventCatagoryEnum.RELIGIOUS.getName()))
			category = EventCatagoryEnum.RELIGIOUS;

		if (category == null)
			throw new BadRequestException("Invalid Event Category: " + value);
		return category;
	}

	private EventTicketNeededForEnum getTicketNeededFor(String value) {
		EventTicketNeededForEnum ticketNeededFor = null;

		if (value.equalsIgnoreCase(EventTicketNeededForEnum.ALL.getName()))
			ticketNeededFor = EventTicketNeededForEnum.ALL;

		if (value.equalsIgnoreCase(EventTicketNeededForEnum.EIGHTEEN_PLUS.getName()))
			ticketNeededFor = EventTicketNeededForEnum.EIGHTEEN_PLUS;

		if (value.equalsIgnoreCase(EventTicketNeededForEnum.FIVE_PLUS.getName()))
			ticketNeededFor = EventTicketNeededForEnum.FIVE_PLUS;

		if (value.equalsIgnoreCase(EventTicketNeededForEnum.TEN_PLUS.getName()))
			ticketNeededFor = EventTicketNeededForEnum.TEN_PLUS;

		if (ticketNeededFor == null)
			throw new BadRequestException("Invalid Ticket Needed For: " + value);

		return ticketNeededFor;
	}

	private EventAgeGroupEnum getEventAgeGroup(String value) {
		System.out.println("Getting Event Age Group for value: " + value);
		EventAgeGroupEnum ageGroup = null;

		System.out.println(value.equalsIgnoreCase(EventAgeGroupEnum.ALL_AGES.getName()));
		if (value.equalsIgnoreCase(EventAgeGroupEnum.ALL_AGES.getName()))
			ageGroup = EventAgeGroupEnum.ALL_AGES;

		if (value.equalsIgnoreCase(EventAgeGroupEnum.EIGHTEEN_PLUS.getName()))
			ageGroup = EventAgeGroupEnum.EIGHTEEN_PLUS;

		if (value.equalsIgnoreCase(EventAgeGroupEnum.FIVE_PLUS.getName()))
			ageGroup = EventAgeGroupEnum.FIVE_PLUS;

		if (value.equalsIgnoreCase(EventAgeGroupEnum.SENIOR_CITIZEN.getName()))
			ageGroup = EventAgeGroupEnum.SENIOR_CITIZEN;

		if (ageGroup == null)
			throw new BadRequestException("Invalid Event Age Group: " + value);

		return ageGroup;
	}

	private TicketTypeEnum getTicketType(String value) {
		TicketTypeEnum ticketType = null;

		if (value.equalsIgnoreCase(TicketTypeEnum.FREE.getName()))
			ticketType = TicketTypeEnum.FREE;
		if (value.equalsIgnoreCase(TicketTypeEnum.PAID.getName()))
			ticketType = TicketTypeEnum.PAID;
		if (ticketType == null)
			throw new BadRequestException("Invalid Ticket Type: " + value);
		return ticketType;
	}

	public void convertDateAndTimeDtoToEntity(AddDateAndTimeRequestDto dto, EventDetails event) {

		if (dto.getEventStartDateTime() != null) {
			event.setEventStartDateTime(dto.getEventStartDateTime());
		}

		if (dto.getEventEndDateTime() != null) {
			event.setEventEndDateTime(dto.getEventEndDateTime());
		}

		event.setUpdatedOn(LocalDateTime.now());
	}

	public void convertLocationDetails(AddLocationDetailsRequestDto dto, EventDetails event) {

		event.setEventVenueLocaltion(dto.getEventVenueLocaltion());
		event.setEventAddressLine1(dto.getEventAddressLine1());
		event.setEventAddressLine2(dto.getEventAddressLine2());
		event.setEventCity(dto.getEventCity());
		event.setEventState(dto.getEventState());
		event.setEventType(geteventType(dto.getEventType()));
		if (event.getEventType() == EventTypeEnum.PRIVATE) {
			event.setPrivateEvent(true);
		}
		event.setEventCountry(dto.getEventCountry());
		event.setEventZipCode(dto.getEventZipCode());
		event.setGoogleMapLocationLink(dto.getGoogleMapLocationLink());
	}

	public void convertTicketsAndPricingDetails(AddTicketsAndPricingDetailsDto dto, EventDetails event) {
		OrganizerDetails organizer = organizerRepository.findByOrgId(event.getOrganizerId());
		event.setTicketType(getTicketType(dto.getTicketType()));
		event.setSalseStartDateTime(dto.getSalesStartDateAndTime());
		event.setSalseEndDateTime(dto.getSalesEndDateAndTime());

		ticketTypeService.saveTicketTypes(dto.getTicketTypeDetails(), event, organizer);

	}

	public void convertEventPoliciesDtoToEntity(AddEventPoliciesRequestDto dto, EventDetails event) {
		event.setEventTermsAndConditions(dto.getTearmsAndConditions());
		
	}

	public void convertPostEventDetailsDtoToEntity(Boolean isPostEvent, Boolean isEventInDraft, EventDetails event) {
		event.setIsPostEvent(isPostEvent);
		event.setIsEventInDraft(isEventInDraft);
		event.setUpdatedOn(LocalDateTime.now());
	}

}
