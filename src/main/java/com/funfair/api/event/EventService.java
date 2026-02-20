package com.funfair.api.event;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.funfair.api.account.role.RoleType;
import com.funfair.api.account.user.User;
import com.funfair.api.account.user.UserRepository;
import com.funfair.api.account.user.UserService;
import com.funfair.api.account.userrole.UserRoleService;
import com.funfair.api.common.GeoUtil;
import com.funfair.api.common.ImagePathUrl;
import com.funfair.api.common.Util;
import com.funfair.api.event.addeventdetailsdtos.AddBasicInfoRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddDateAndTimeRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddEventPoliciesRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddLocationDetailsRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddManageRoleDetailDto;
import com.funfair.api.event.addeventdetailsdtos.AddOrgDetailsRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddTicketsAndPricingDetailsDto;
import com.funfair.api.event.addeventdetailsdtos.ConvertDtoToEntityService;
import com.funfair.api.event.eventartistsdetails.EventArtistsDetails;
import com.funfair.api.event.eventartistsdetails.EventArtistsRepository;
import com.funfair.api.event.galleryimages.GalleryImagesDetails;
import com.funfair.api.event.galleryimages.GalleryImagesRepository;
import com.funfair.api.event.gatenodetails.AddGateNoDetailsDto;
import com.funfair.api.event.gatenodetails.GateNoDetailsService;
import com.funfair.api.event.salespersonticketdetails.AddSalesPersonTicketDto;
import com.funfair.api.event.salespersonticketdetails.SalespersonTicketService;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.organizer.OrganizerDetails;
import com.funfair.api.organizer.OrganizerRepository;
import com.funfair.api.organizer.OrganizerService;
import com.funfair.api.ticketavailability.AddTicketAvilabilityDetailDto;
import com.funfair.api.ticketavailability.AvilableTicketDetailsDto;
import com.funfair.api.ticketavailability.TicketAvailabilityService;
import com.funfair.api.ticketavailability.TicketAvilableDetailsDto;
import com.funfair.api.ticketavailability.tickettypeacailability.AddTicketTypeAvilabilityDetailsDto;
import com.funfair.api.tickettypedetails.GetTicketDetailsDto;
import com.funfair.api.tickettypedetails.TicketTypeDetails;
import com.funfair.api.tickettypedetails.TicketTypeService;

import jakarta.transaction.Transactional;

@Service
public class EventService {

	private static final Logger LOG = LoggerFactory.getLogger(EventService.class);

	@Autowired
	EventRepository eventRepository;
	@Autowired
	OrganizerService organizerService;
	@Autowired
	TicketTypeService ticketTypeService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	GalleryImagesRepository galleryImagesRepository;
	@Autowired
	EventArtistsRepository eventArtistsRepository;
	@Autowired
	GateNoDetailsService gateNoDetailsService;
	@Autowired
	ConvertDtoToEntityService convertDtoToEntityService;
	@Autowired
	OrganizerRepository organizerRepository;
	@Autowired
	SalespersonTicketService salespersonTicketService;
	@Autowired
	TicketAvailabilityService ticketAvailabilityService;
	@Autowired
	UserService userService;
	@Autowired
	Util util;

	public List<GetFullEventDetailsDto> getAllEvents() {

		LOG.info("Fetching all events");

		List<EventDetails> events = eventRepository.findAll();
		LOG.debug("Total events fetched from DB: {}", events.size());

		List<GetFullEventDetailsDto> eventDtos = new ArrayList<>();

		for (EventDetails event : events) {
			LOG.debug("Converting event with ID: {}", event.getEventId());
			GetFullEventDetailsDto dto = convertToFullEventDto(event);
			eventDtos.add(dto);
		}
		LOG.info("Successfully converted {} events", eventDtos.size());

		return eventDtos;
	}

	private String calculateEventStatus(EventDetails event) {

		LOG.debug("Calculating status for event ID: {}", event.getEventId());

		// 1️⃣ Draft
		if (Boolean.TRUE.equals(event.isEventInDraft())) {
			LOG.debug("Event ID {} is in DRAFT state", event.getEventId());
			return EventCurrentStatus.DRAFT.getName();
		}

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = event.getEventStartDateTime();
		LocalDateTime end = event.getEventEndDateTime();

		LOG.debug("Event ID {} timing check | Now: {}, Start: {}, End: {}", event.getEventId(), now, start, end);

		// Safety check
		if (start == null || end == null) {
			LOG.warn("Event ID {} has missing start/end time (start={}, end={}), marking as UPCOMING",
					event.getEventId(), start, end);
			return EventCurrentStatus.UPCOMING.getName();
		}

		// 2️⃣ Upcoming
		if (now.isBefore(start)) {
			LOG.debug("Event ID {} is UPCOMING", event.getEventId());
			return EventCurrentStatus.UPCOMING.getName();
		}

		// 3️⃣ Live
		if ((now.isEqual(start) || now.isAfter(start)) && now.isBefore(end)) {
			LOG.debug("Event ID {} is LIVE", event.getEventId());
			return EventCurrentStatus.LIVE.getName();
		}

		// 4️⃣ Completed
		LOG.debug("Event ID {} is COMPLETED", event.getEventId());
		return EventCurrentStatus.COMPLETED.getName();
	}

	private GetFullEventDetailsDto convertToFullEventDto(EventDetails event) {

		LOG.debug("Converting EventDetails to DTO for event ID: {}", event.getEventId());

		GetFullEventDetailsDto dto = new GetFullEventDetailsDto();

		// Event basic details
		dto.setOrganizerId(event.getOrganizerId());
		dto.setEventId(event.getEventId());
		dto.setEventTitle(event.getEventTitle());
		dto.setEventCatagory(event.getEventCatagory().getName());
		dto.setEventTags(event.getEventTags());
		dto.setEventShortDescription(event.getEventShortDescription());
		dto.setEntryAllowFor(event.getEntryAllowFor().getName());
		dto.setTicketNeededFor(event.getTicketNeededFor().getName());
		dto.setEventStartDateTime(event.getEventStartDateTime());
		dto.setEventEndDateTime(event.getEventEndDateTime());

		dto.setEventBannerImageUrl(ImagePathUrl.BANNER_IMAGE_PATH + event.getEventBannerImageUrl());
		dto.setEventThumbnailImageUrl(ImagePathUrl.THUMBNAIL_IMAGE_PATH + event.getThumbnailImageUrl());

		// Gallery images
		List<GalleryImagesDetails> galleryImages = galleryImagesRepository.findByEventId(event.getEventId());

		LOG.debug("Found {} gallery images for event ID: {}", galleryImages.size(), event.getEventId());

		List<String> galleryImageUrls = new ArrayList<>();
		for (GalleryImagesDetails img : galleryImages) {
			galleryImageUrls.add(ImagePathUrl.GALLERY_IMAGE_PATH + img.getImageUrl());
		}
		dto.setEventGalleryImageUrl(galleryImageUrls);

		// Location & type details
		dto.setEventType(event.getEventType().getName());
		dto.setEventVenueLocaltion(event.getEventVenueLocaltion());
		dto.setEventAddressLine1(event.getEventAddressLine1());
		dto.setEventAddressLine2(event.getEventAddressLine2());
		dto.setEventCity(event.getEventCity());
		dto.setEventState(event.getEventState());
		dto.setEventCountry(event.getEventCountry());
		dto.setEventZipCode(event.getEventZipCode());
		dto.setGoogleMapLocationLink(event.getGoogleMapLocationLink());

		// Ticket & sales info
		dto.setTicketType(event.getTicketType().getName());
		dto.setSalseStartDateTime(event.getSalseStartDateTime());
		dto.setSalseEndDateTime(event.getSalseEndDateTime());
		dto.setEventTermAndConditions(event.getEventTermsAndConditions());

		dto.setIsPrivateEvent(event.isPrivateEvent());
		dto.setCurrentStatus(calculateEventStatus(event));
		dto.setPrivateEventLink(event.getPrivateEventLink());

		// Door Managers
		LOG.debug("Fetching door managers for event ID: {}", event.getEventId());
		dto.setDoorManagerDetails(userRoleService.getDoorManagersByEventId(event.getEventId(), event.getOrganizerId()));

		// Sales Persons
		LOG.debug("Fetching sales persons for event ID: {}", event.getEventId());
		dto.setSalesPersonDetails(userRoleService.getSalesPersonsByEventId(event.getEventId()));

		// Ticket Types
		LOG.debug("Fetching ticket types for event ID: {}", event.getEventId());
		dto.setTicketTypeDetails(ticketTypeService.getTicketsByEventId(event.getEventId()));

		LOG.debug("Successfully converted event ID {} to DTO", event.getEventId());

		return dto;
	}

	public GetFullEventDetailsDto getEventById(String eventId) {

		LOG.info("Fetching event details for event ID: {}", eventId);

		EventDetails event = eventRepository.findByEventId(eventId);

		if (event == null) {
			LOG.warn("Event not found for event ID: {}", eventId);
			return null; // or throw exception if you prefer
		}

		GetFullEventDetailsDto dto = convertToFullEventDto(event);

		LOG.info("Successfully fetched event details for event ID: {}", eventId);

		return dto;
	}

	@Transactional
	public EventDetails addEventImages(AddEventImagesDto dto, String thumbnailUploadDir, String bannerUploadDir,
			String galleryUploadDir) {

		LOG.info("Starting image upload for event ID: {}", dto.getEventId());

		// 1️⃣ Fetch Event
		EventDetails event = eventRepository.findByEventId(dto.getEventId());
		if (event == null) {
			LOG.warn("Event not found for image upload, event ID: {}", dto.getEventId());
			throw new BadRequestException("Event not found with ID: " + dto.getEventId());
		}

		try {

			// 2️⃣ Thumbnail Image
			if (dto.getThumbnailImage() != null && !dto.getThumbnailImage().isEmpty()) {

				LOG.debug("Uploading thumbnail image for event ID: {}", dto.getEventId());

				String thumbnailName = util.saveFile(dto.getThumbnailImage(), thumbnailUploadDir);

				event.setThumbnailImageUrl(thumbnailName);

				LOG.debug("Thumbnail image uploaded successfully: {}", thumbnailName);
			}

			// 3️⃣ Banner Image
			if (dto.getBannerImage() != null && !dto.getBannerImage().isEmpty()) {

				LOG.debug("Uploading banner image for event ID: {}", dto.getEventId());

				String bannerName = util.saveFile(dto.getBannerImage(), bannerUploadDir);

				event.setEventBannerImageUrl(bannerName);

				LOG.debug("Banner image uploaded successfully: {}", bannerName);
			}

			// 4️⃣ Gallery Images (MULTIPLE)
			if (dto.getGalleryImage() != null && dto.getGalleryImage().length > 0) {

				LOG.debug("Uploading {} gallery images for event ID: {}", dto.getGalleryImage().length,
						dto.getEventId());

				List<GalleryImagesDetails> newImages = new ArrayList<>();

				for (MultipartFile file : dto.getGalleryImage()) {

					if (file != null && !file.isEmpty()) {

						String fileName = util.saveFile(file, galleryUploadDir);

						GalleryImagesDetails gallery = new GalleryImagesDetails();
						gallery.setEventId(dto.getEventId());
						gallery.setImageUrl(fileName);
						gallery.setCreatedBy("system");
						gallery.setCreatedOn(LocalDateTime.now());
						gallery.setActive(true);

						newImages.add(gallery);

						LOG.debug("Gallery image uploaded: {}", fileName);
					}
				}

				galleryImagesRepository.saveAll(newImages);

				LOG.info("Saved {} gallery images for event ID: {}", newImages.size(), dto.getEventId());
			}

			// 5️⃣ Save Event
			EventDetails savedEvent = eventRepository.save(event);

			LOG.info("Successfully completed image upload for event ID: {}", dto.getEventId());

			return savedEvent;

		} catch (IOException e) {

			LOG.error("Image upload failed for event ID: {}", dto.getEventId(), e);
			throw new RuntimeException("Image upload failed", e);
		}
	}

	public EventDetails createEventDetails(AddOrgDetailsRequestDto addOrgDetailsRequestDto) {

		LOG.info("Creating event details for org ID: {}", addOrgDetailsRequestDto.getOrgId());

		if (addOrgDetailsRequestDto.getOrgId() == null || addOrgDetailsRequestDto.getOrgId().isEmpty()) {

			LOG.warn("Org ID is missing while creating event");
			throw new BadRequestException("orgId is not present");
		}

		EventDetails event = convertDtoToEntityService.convertOrgDetailsDtoToEntity(addOrgDetailsRequestDto);

		eventRepository.save(event);

		LOG.info("Event created successfully with event ID: {}", event.getEventId());

		return event;
	}

	public EventDetails addBasicInfo(AddBasicInfoRequestDto dto) {

		LOG.info("Adding basic info for event ID: {}", dto.getEventId());

		if (dto.getEventId() == null || dto.getEventId().isEmpty()) {
			throw new BadRequestException("eventId is required");
		}

		EventDetails event = eventRepository.findByEventId(dto.getEventId());
		if (event == null) {
			throw new BadRequestException("Event not found with ID: " + dto.getEventId());
		}

		convertDtoToEntityService.convertBasicInfoDtoToEntity(dto, event);
		eventRepository.save(event);

		if (dto.getArtistsIds() != null && !dto.getArtistsIds().isEmpty()) {
			for (String artistId : dto.getArtistsIds()) {

				EventArtistsDetails artistDetails = new EventArtistsDetails();
				artistDetails.setEventId(dto.getEventId());
				artistDetails.setArtistId(artistId);
				artistDetails.setCreatedOn(LocalDateTime.now());
				artistDetails.setCreatedBy(event.getCreatedBy());
				eventArtistsRepository.save(artistDetails);
			}
		}

		LOG.info("Basic info + artists updated successfully for event ID: {}", dto.getEventId());

		return event;
	}

	public EventDetails addDateAndTime(AddDateAndTimeRequestDto dto) {

		LOG.info("Adding date and time info for event ID: {}", dto.getEventId());

		if (dto.getEventId() == null || dto.getEventId().isEmpty()) {

			LOG.warn("Event ID is missing while adding date and time info");
			throw new BadRequestException("eventId is required");
		}

		EventDetails event = eventRepository.findByEventId(dto.getEventId());
		if (event == null) {

			LOG.warn("Event not found while adding date and time info, event ID: {}", dto.getEventId());
			throw new BadRequestException("Event not found with ID: " + dto.getEventId());
		}

		// Update date & time info
		LOG.debug("Updating date and time details for event ID: {}", dto.getEventId());
		convertDtoToEntityService.convertDateAndTimeDtoToEntity(dto, event);

		eventRepository.save(event);

		LOG.info("Date and time info updated successfully for event ID: {}", dto.getEventId());

		return event;
	}

	public EventDetails addLocationDetails(AddLocationDetailsRequestDto dto) {

		LOG.info("Adding location details for event ID: {}", dto.getEventId());

		if (dto.getEventId() == null || dto.getEventId().isEmpty()) {
			LOG.warn("Event ID is missing while adding location details");
			throw new BadRequestException("eventId is required");
		}

		EventDetails event = eventRepository.findByEventId(dto.getEventId());
		if (event == null) {
			LOG.warn("Event not found while adding location details, event ID: {}", dto.getEventId());
			throw new BadRequestException("Event not found with ID: " + dto.getEventId());
		}

		// Convert & update location info
		LOG.debug("Updating location details for event ID: {}", dto.getEventId());
		convertDtoToEntityService.convertLocationDetails(dto, event);

		EventDetails savedEvent = eventRepository.save(event);

		LOG.info("Location details updated successfully for event ID: {}", dto.getEventId());

		return savedEvent;
	}

	public EventDetails addTicketsAndPricing(AddTicketsAndPricingDetailsDto dto) {

		LOG.info("Adding tickets and pricing details for event ID: {}", dto.getEventId());

		if (dto.getEventId() == null || dto.getEventId().isEmpty()) {
			LOG.warn("Event ID is missing while adding tickets and pricing details");
			throw new BadRequestException("eventId is required");
		}

		EventDetails event = eventRepository.findByEventId(dto.getEventId());
		if (event == null) {
			LOG.warn("Event not found while adding tickets and pricing, event ID: {}", dto.getEventId());
			throw new BadRequestException("Event not found with ID: " + dto.getEventId());
		}

		// Convert & update tickets and pricing info
		LOG.debug("Updating tickets and pricing details for event ID: {}", dto.getEventId());
		convertDtoToEntityService.convertTicketsAndPricingDetails(dto, event);

		EventDetails savedEvent = eventRepository.save(event);
		LOG.info("Tickets and pricing updated successfully for event ID: {}", dto.getEventId());

		AddTicketAvilabilityDetailDto availabilityDto = convertToAvailabilityDto(dto, event);

		ticketAvailabilityService.addTicketAvailabilityDetails(availabilityDto);

		return savedEvent;
	}

	private AddTicketAvilabilityDetailDto convertToAvailabilityDto(AddTicketsAndPricingDetailsDto dto,
			EventDetails event) {

		AddTicketAvilabilityDetailDto availabilityDto = new AddTicketAvilabilityDetailDto();

		availabilityDto.setEventId(event.getEventId());

		availabilityDto.setOrgId(event.getOrganizerId());

		availabilityDto.setCreatedBy(event.getCreatedBy());

		availabilityDto.setBookingPrice(0);
		int totalTickets = 0;

		List<GetTicketDetailsDto> ticketTypeDetails = ticketTypeService.getTicketsByEventId(event.getEventId());

		List<AddTicketTypeAvilabilityDetailsDto> typeList = new ArrayList<>();

		for (GetTicketDetailsDto ticket : ticketTypeDetails) {

			AddTicketTypeAvilabilityDetailsDto typeDto = new AddTicketTypeAvilabilityDetailsDto();

			typeDto.setTicketTypeId(ticket.getTicketTypeId());

			typeDto.setTicketName(ticket.getTicketName());

			typeDto.setTicketPrice(ticket.getTicketPrice());

			typeDto.setQuntityAvialable(ticket.getQuntityAvialable());

			totalTickets += ticket.getQuntityAvialable();

			typeList.add(typeDto);
		}

		availabilityDto.setTotalTickets(totalTickets);

		availabilityDto.setTicketTypeAvilabilityDetails(typeList);

		return availabilityDto;
	}

	public EventDetails addEventPolicies(AddEventPoliciesRequestDto dto) {

		LOG.info("Adding event policies for event ID: {}", dto.getEventId());

		if (dto.getEventId() == null || dto.getEventId().isEmpty()) {
			LOG.warn("Event ID is missing while adding event policies");
			throw new BadRequestException("eventId is required");
		}

		EventDetails event = eventRepository.findByEventId(dto.getEventId());
		if (event == null) {
			LOG.warn("Event not found while adding event policies, event ID: {}", dto.getEventId());
			throw new BadRequestException("Event not found with ID: " + dto.getEventId());
		}

		// Convert & update event policies info
		LOG.debug("Updating event policies for event ID: {}", dto.getEventId());
		convertDtoToEntityService.convertEventPoliciesDtoToEntity(dto, event);

		eventRepository.save(event);

		LOG.info("Event policies updated successfully for event ID: {}", dto.getEventId());

		return event;
	}

	public EventDetails manageEventRoles(AddManageRoleDetailDto dto) {

		LOG.info("Managing role '{}' for event ID: {}", dto.getRole(), dto.getEventId());

		if (dto.getEventId() == null || dto.getEventId().isEmpty()) {
			LOG.warn("Event ID is missing while managing event roles");
			throw new BadRequestException("eventId is required");
		}

		if (dto.getRole() == null || dto.getRole().isEmpty()) {
			LOG.warn("Role is missing while managing event roles");
			throw new BadRequestException("role is required");
		}

		EventDetails event = eventRepository.findByEventId(dto.getEventId());
		if (event == null) {
			LOG.warn("Event not found while managing roles, event ID: {}", dto.getEventId());
			throw new BadRequestException("Event not found");
		}

		OrganizerDetails organizer = organizerRepository.findByOrgId(dto.getOrgid());
		if (organizer == null) {
			LOG.warn("Organizer not found while managing roles, org ID: {}", dto.getOrgid());
			throw new BadRequestException("Organizer not found");
		}

		// 🔹 Find or create user
		User user = userRepository.findByPhoneNo(dto.getUserphoneNumber());

		if (user == null) {
			LOG.info("User not found, creating new user with phone: {}", dto.getUserphoneNumber());

			user = new User();
			user.setUserId(util.generateRandomNumericId());
			user.setUserName(dto.getUserName());
			user.setPhoneNo(dto.getUserphoneNumber());
			user.setEmailId(dto.getUserEmail());
			user.setCreatedOn(LocalDateTime.now());

			user = userRepository.save(user);

			LOG.info("New user created with user ID: {}", user.getUserId());
		} else {
			LOG.debug("Existing user found with user ID: {}", user.getUserId());
		}

		// 🔹 Assign role
		LOG.info("Assigning role '{}' to user ID {} for event ID {}", dto.getRole(), user.getUserId(),
				event.getEventId());

		userRoleService.assignRoleToUser(user, dto.getRole(), event, dto.getRoleStartDateTime(),
				dto.getRoleEndDateTime(), organizer);

		// 🔹 SALESPERSON → ticket quantity mapping
		if (RoleType.SALESPERSON.getName().equalsIgnoreCase(dto.getRole())) {

			LOG.debug("Processing salesperson ticket mapping for user ID: {}", user.getUserId());

			if (dto.getAddSalesPersonTicketDtos() != null) {
				for (AddSalesPersonTicketDto ticketDto : dto.getAddSalesPersonTicketDtos()) {

					ticketDto.setEventId(event.getEventId());
					ticketDto.setSalespersonId(user.getUserId());
					ticketDto.setCreatedBy(organizer.getOrgUserId());

					salespersonTicketService.addSalespersonTicket(ticketDto);
				}
			}
		}

		// 🔹 DOOR MANAGER → gate mapping
		if (RoleType.DOORMANAGER.getName().equalsIgnoreCase(dto.getRole())) {

			LOG.debug("Processing gate assignment for door manager user ID: {}", user.getUserId());

			AddGateNoDetailsDto gateDto = new AddGateNoDetailsDto();
			gateDto.setEventId(event.getEventId());
			gateDto.setGateNoDetails(dto.getGateNoDetails());
			gateDto.setUserId(user.getUserId());
			gateDto.setOrgId(organizer.getOrgId());

			gateNoDetailsService.saveGateNoDetails(gateDto);
		}

		LOG.info("Role '{}' managed successfully for event ID: {}", dto.getRole(), event.getEventId());

		return event;
	}

	public EventDetails addPostEventDetails(Boolean isPostEvent, Boolean isEventInDraft, String eventId) {
		if (eventId == null || eventId.isEmpty()) {
			throw new BadRequestException("eventId is required");
		}
		EventDetails event = eventRepository.findByEventId(eventId);
		if (event == null) {
			throw new BadRequestException("Event not found with ID: " + eventId);
		}
		// convert & update post event info
		convertDtoToEntityService.convertPostEventDetailsDtoToEntity(isPostEvent, isEventInDraft, event);
		eventRepository.save(event);
		return event;
	}

	public List<GetFullEventDetailsDto> getEventsByOrganizerId(String organizerId) {
		List<EventDetails> events = eventRepository.findByOrganizerId(organizerId);
		List<GetFullEventDetailsDto> eventDtos = new ArrayList<>();
		for (EventDetails event : events) {
			GetFullEventDetailsDto dto = convertToFullEventDto(event);
			eventDtos.add(dto);
		}
		return eventDtos;
	}

	public List<CustomerHomeEventDetailsDto> getThisWeekEventDetailsForCustomer() {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59,
				59);

		List<EventDetails> events = eventRepository
				.findByEventStartDateTimeLessThanEqualAndEventEndDateTimeGreaterThanEqual(endOfWeek, now);

		LOG.info("Found {} live/active events this week", events.size());

		return events.stream().map(this::convertToCustomerHomeDto).toList();
	}

	public List<CustomerHomeEventDetailsDto> getAllEventDetailsForCustomer() {

		List<EventDetails> events = eventRepository
				.findByIsActiveTrueAndIsPrivateEventFalseAndIsEventInDraftFalseAndIsPostEventFalse();

		return events.stream().map(this::convertToCustomerHomeDto).toList();
	}

	private CustomerHomeEventDetailsDto convertToCustomerHomeDto(EventDetails eventDetails) {

		CustomerHomeEventDetailsDto dto = new CustomerHomeEventDetailsDto();
		dto.setEventId(eventDetails.getEventId());
		dto.setEventName(eventDetails.getEventTitle());
		Double eventPrice = ticketTypeService.getLowestTicketPriceByEventId(eventDetails.getEventId());
		dto.setEventprice(eventPrice);
		dto.setEventStartDateTime(
				eventDetails.getEventStartDateTime() != null ? eventDetails.getEventStartDateTime().toString() : null);
		dto.setEventEndDateTime(
				eventDetails.getEventEndDateTime() != null ? eventDetails.getEventEndDateTime().toString() : null);
		dto.setEventAddressLine1(eventDetails.getEventAddressLine1());
		dto.setEventAddressLine2(eventDetails.getEventAddressLine2());
		dto.setThumbnailImageUrl(ImagePathUrl.THUMBNAIL_IMAGE_PATH + eventDetails.getThumbnailImageUrl());

		return dto;
	}

	public List<CustomerHomeEventDetailsDto> filterEventsByDate(LocalDate startDate, LocalDate endDate) {

		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

		List<EventDetails> eventDetails = eventRepository.findByEventStartDateTimeBetween(startDateTime, endDateTime);

		List<CustomerHomeEventDetailsDto> dtos = new ArrayList<>();

		for (EventDetails eventDetail : eventDetails) {
			dtos.add(convertToCustomerHomeDto(eventDetail));
		}

		return dtos;
	}

	public List<CustomerHomeEventDetailsDto> findNearbyEvents(double userLat, double userLon, double radiusKm) {

		List<EventDetails> all = eventRepository.findByIsActiveTrue();

		return all.stream()

				// remove null coordinates
				.filter(e -> e.getEventLatitude() != null && e.getEventLongitude() != null)

				// distance filter
				.filter(e -> {
					double dist = GeoUtil.distanceKm(userLat, userLon, e.getEventLatitude(), e.getEventLongitude());
					return dist <= radiusKm;
				}).map(this::convertToCustomerHomeDto).toList();
	}

	public List<CustomerHomeEventDetailsDto> getEventsByCatagory(String catagory) {
		EventCatagoryEnum catagoryEnum = convertDtoToEntityService.getEventCategory(catagory);
		List<EventDetails> events = eventRepository.findByEventCatagory(catagoryEnum);
		List<CustomerHomeEventDetailsDto> eventDtos = new ArrayList<>();
		for (EventDetails eventDetails : events) {
			CustomerHomeEventDetailsDto dto = convertToCustomerHomeDto(eventDetails);
			eventDtos.add(dto);
		}
		return eventDtos;
	}

	public List<CustomerHomeEventDetailsDto> getEventsByArtistsId(String artistsId) {
		List<EventArtistsDetails> eventArtists = eventArtistsRepository.findByArtistId(artistsId);
		List<CustomerHomeEventDetailsDto> dtos = new ArrayList<>();

		LocalDateTime now = LocalDateTime.now();
		for (EventArtistsDetails ea : eventArtists) {

			EventDetails event = eventRepository.findByEventIdAndEventEndDateTimeAfter(ea.getEventId(), now);
			if (event != null) {
				dtos.add(convertToCustomerHomeDto(event));
			}
		}
		return dtos;
	}

	public TicketAvilableDetailsDto checkAvailableTickets(String eventId) {

		EventDetails event = eventRepository.findByEventId(eventId);
		List<AvilableTicketDetailsDto> availableTickets = ticketTypeService.getAvailableTicketsByEventId(eventId);
		TicketAvilableDetailsDto dto = new TicketAvilableDetailsDto();
		dto.setEventname(event.getEventTitle());
		dto.setEventStartDateTime(event.getEventStartDateTime());
		dto.setEventEndDateTime(event.getEventEndDateTime());
		dto.setAvailableTicketDetails(availableTickets);
		dto.setEventLocation(
				event.getEventVenueLocaltion() + "," + event.getEventAddressLine1() + "," + event.getEventAddressLine2()
						+ "," + event.getEventCity() + "," + event.getEventState() + "," + event.getEventCountry());
		return dto;
	}

	public List<EventCategoryDto> getAllEventCatagory() {

		List<EventCategoryDto> list = new ArrayList<>();

		for (EventCatagoryEnum category : EventCatagoryEnum.values()) {

			EventCategoryDto dto = new EventCategoryDto(category.name(), category.getName());

			list.add(dto);
		}

		return list;
	}

	public List<CustomerHomeEventDetailsDto> upcomingEvents() {

		LocalDateTime now = LocalDateTime.now();

		List<EventDetails> upcomingEvents = eventRepository
				.findByEventStartDateTimeAfterOrderByEventStartDateTimeAsc(now);

		return upcomingEvents.stream().map(this::convertToCustomerHomeDto).toList();
	}

}
