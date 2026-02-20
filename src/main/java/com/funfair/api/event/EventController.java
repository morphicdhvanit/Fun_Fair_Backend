package com.funfair.api.event;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.funfair.api.event.addeventdetailsdtos.AddBasicInfoRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddDateAndTimeRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddEventPoliciesRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddLocationDetailsRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddManageRoleDetailDto;
import com.funfair.api.event.addeventdetailsdtos.AddOrgDetailsRequestDto;
import com.funfair.api.event.addeventdetailsdtos.AddTicketsAndPricingDetailsDto;

@RestController
@RequestMapping("/event")
@CrossOrigin("*")
public class EventController {
	@Value("${app.upload.event-thumbnail-image-path}")
	private String thumbnailImagePath;
	@Value("${app.upload.event-Banner-image-path}")
	private String bannerImagePath;
	@Value("${app.upload.event-gallery-image-path}")
	private String galleryImagePath;

	@Autowired
	EventService eventService;

	@PostMapping("/add/create-event")
	public ResponseEntity<EventDetails> createEventDetails(
			@RequestBody AddOrgDetailsRequestDto addOrgDetailsRequestDto) {
		EventDetails event = eventService.createEventDetails(addOrgDetailsRequestDto);
		return new ResponseEntity<EventDetails>(event, HttpStatus.OK);
	}

	@PostMapping("/add/basic-info")
	public ResponseEntity<EventDetails> addBasicInfo(@RequestBody AddBasicInfoRequestDto addOrgDetailsRequestDto) {
		EventDetails event = eventService.addBasicInfo(addOrgDetailsRequestDto);
		return new ResponseEntity<EventDetails>(event, HttpStatus.OK);
	}

	@PostMapping("/add/post-event/{eventId}")
	public ResponseEntity<EventDetails> addPostEventDetails(@PathVariable String eventId,
			@RequestParam Boolean isPostEvent, @RequestParam Boolean isEventInDraft) {
		EventDetails response = eventService.addPostEventDetails(isPostEvent, isEventInDraft, eventId);
		return new ResponseEntity<EventDetails>(response, HttpStatus.OK);
	}

	@PostMapping("/add/date-time")
	public ResponseEntity<EventDetails> addDateAndTime(@RequestBody AddDateAndTimeRequestDto dto) {
		EventDetails response = eventService.addDateAndTime(dto);
		return new ResponseEntity<EventDetails>(response, HttpStatus.OK);
	}

	@PostMapping("/add/Location")
	public ResponseEntity<EventDetails> addLocationDetails(@RequestBody AddLocationDetailsRequestDto dto) {
		EventDetails response = eventService.addLocationDetails(dto);
		return new ResponseEntity<EventDetails>(response, HttpStatus.OK);
	}

	@PostMapping("/add/images")
	public ResponseEntity<EventDetails> addEventImages(@ModelAttribute AddEventImagesDto dto) {
		EventDetails response = eventService.addEventImages(dto, thumbnailImagePath, bannerImagePath, galleryImagePath);
		return new ResponseEntity<EventDetails>(response, HttpStatus.OK);
	}

	@GetMapping("/all-catagory")
	public ResponseEntity<List<EventCategoryDto>> getAllEventCatagory() {
		List<EventCategoryDto> response = eventService.getAllEventCatagory();
		return new ResponseEntity<List<EventCategoryDto>>(response, HttpStatus.OK);
	}

	@PostMapping("/add/tickets-pricing")
	public ResponseEntity<EventDetails> addTicketsAndPricing(@RequestBody AddTicketsAndPricingDetailsDto dto) {
		EventDetails response = eventService.addTicketsAndPricing(dto);
		return new ResponseEntity<EventDetails>(response, HttpStatus.OK);
	}

	@PostMapping("/add/policies")
	public ResponseEntity<EventDetails> addEventPolicies(@RequestBody AddEventPoliciesRequestDto dto) {
		EventDetails response = eventService.addEventPolicies(dto);
		return new ResponseEntity<EventDetails>(response, HttpStatus.OK);
	}

	@PostMapping("/add/manage-roles")
	public ResponseEntity<EventDetails> manageEventRoles(@RequestBody AddManageRoleDetailDto dto) {
		EventDetails response = eventService.manageEventRoles(dto);
		return new ResponseEntity<EventDetails>(response, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<GetFullEventDetailsDto>> getallEvents() {
		List<GetFullEventDetailsDto> events = eventService.getAllEvents();
		return new ResponseEntity<List<GetFullEventDetailsDto>>(events, HttpStatus.OK);
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<GetFullEventDetailsDto> getEventById(@PathVariable String eventId) {
		GetFullEventDetailsDto response = eventService.getEventById(eventId);
		return new ResponseEntity<GetFullEventDetailsDto>(response, HttpStatus.OK);
	}

	@GetMapping("/org/{organizerId}")
	public ResponseEntity<List<GetFullEventDetailsDto>> getEventsByOrganizerId(@PathVariable String organizerId) {
		List<GetFullEventDetailsDto> response = eventService.getEventsByOrganizerId(organizerId);
		return new ResponseEntity<List<GetFullEventDetailsDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/customer/home/this-week-events")
	public ResponseEntity<List<CustomerHomeEventDetailsDto>> getThisWeekEventDetailsForCustomer() {
		List<CustomerHomeEventDetailsDto> response = eventService.getThisWeekEventDetailsForCustomer();
		return new ResponseEntity<List<CustomerHomeEventDetailsDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/customer/home/all-events")
	public ResponseEntity<List<CustomerHomeEventDetailsDto>> getAllEventDetailsForCustomer() {
		List<CustomerHomeEventDetailsDto> response = eventService.getAllEventDetailsForCustomer();
		return new ResponseEntity<List<CustomerHomeEventDetailsDto>>(response, HttpStatus.OK);
	}

	@PostMapping("/filter/date")
	public ResponseEntity<List<CustomerHomeEventDetailsDto>> filterEventsByDate(@RequestParam LocalDate StartDate,
			@RequestParam LocalDate EndDate) {
		List<CustomerHomeEventDetailsDto> response = eventService.filterEventsByDate(StartDate, EndDate);
		return new ResponseEntity<List<CustomerHomeEventDetailsDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/within-20-km")
	public ResponseEntity<List<CustomerHomeEventDetailsDto>> nearbyEvents(@RequestParam double lat,
			@RequestParam double lon) {
		List<CustomerHomeEventDetailsDto> nearbyEvents = eventService.findNearbyEvents(lat, lon, 20);
		return new ResponseEntity<List<CustomerHomeEventDetailsDto>>(nearbyEvents, HttpStatus.OK);
	}

	@GetMapping("/by-catagory")
	public ResponseEntity<List<CustomerHomeEventDetailsDto>> getEventsByCatagory(@RequestParam String catagory) {
		List<CustomerHomeEventDetailsDto> response = eventService.getEventsByCatagory(catagory);
		return new ResponseEntity<List<CustomerHomeEventDetailsDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/upcoming-events")
	public ResponseEntity<List<CustomerHomeEventDetailsDto>> upcomingEvents() {
		List<CustomerHomeEventDetailsDto> response = eventService.upcomingEvents();
		return new ResponseEntity<List<CustomerHomeEventDetailsDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/by-artists-id/{artistsId}")
	public ResponseEntity<List<CustomerHomeEventDetailsDto>> getEventsByArtistsId(@PathVariable String artistsId) {
		List<CustomerHomeEventDetailsDto> response = eventService.getEventsByArtistsId(artistsId);
		return new ResponseEntity<List<CustomerHomeEventDetailsDto>>(response, HttpStatus.OK);
	}
}
