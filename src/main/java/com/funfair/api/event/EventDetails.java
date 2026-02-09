package com.funfair.api.event;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name  = "event_details")
public class EventDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "event_id", nullable = false, unique = true)
	private String eventId;
	@Column(name  = "event_organizer_name")
	private String eventOrganizerName;
	@Column(name  = "event_organizer_emailId")
	private String eventOrganizerEmailId;
	@Column (name  = "event_organizer_contact_number")
	private String eventOrganizerContactNumber;
	@Column(name = "organizer_id", nullable = false)
	private String organizerId;
	@Column(name = "event_title")
	private String eventTitle;
	@Column(name = "event_catagory")
	@Enumerated(EnumType.STRING)
	private EventCatagoryEnum eventCatagory;
	@Column(name  = "event_tags")
	private String eventTags;
	@Column(name = "event_short_description", length = 2000)
	private String eventShortDescription;
	@Column(name  = "entry_allow_for")
	@Enumerated(EnumType.STRING)
	private EventAgeGroupEnum entryAllowFor;
	@Column(name =  "ticket_needed_for")
	@Enumerated(EnumType.STRING)
	private EventTicketNeededForEnum ticketNeededFor;
	@Column(name  = "event_start_date_time")
	private LocalDateTime eventStartDateTime;
	@Column(name  = "event_end_date_time")
	private LocalDateTime eventEndDateTime;
	@Column(name  = "event_type")
	@Enumerated(EnumType.STRING)
	private EventTypeEnum eventType;
	@Column(name = "event_venue_localtion")
	private String eventVenueLocaltion;
	@Column(name = "event_address_line_1")
	private String eventAddressLine1;
	@Column(name = "event_address_line_2")
	private String eventAddressLine2;
	@Column(name = "event_city")
	private String eventCity;
	@Column(name = "event_state")
	private String eventState;
	@Column(name = "event_country")
	private String eventCountry;
	@Column(name = "event_zip_code")
	private String eventZipCode;
	@Column(name = "google_map_location_link" , length = 2000)
	private String googleMapLocationLink;
	@Column(name = "thumbnail_image_url")
	private String thumbnailImageUrl;
	@Column(name  = "event_banner_image_url")
	private String eventBannerImageUrl;
	@Column(name  = "ticket_type")
	@Enumerated(EnumType.STRING)
	private TicketTypeEnum ticketType;
	@Column(name = "salse_start_date_time")
	private LocalDateTime salseStartDateTime;
	@Column(name = "salse_end_date_time")
	private LocalDateTime salseEndDateTime;
	@Column(name  = "event_terms_and_conditions", length = 5000)
	private String eventTermsAndConditions;
	
	@Column(name  = "is_private_event")
	private boolean isPrivateEvent;
	@Column(name  = "private_event_link")
	private String privateEventLink;
	@Column(name  = "is_event_in_draft")
	private Boolean isEventInDraft;
	@Column(name  = "is_post_event")
	private Boolean isPostEvent;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;
	@Column(name = "is_active")
	private boolean isActive = true;
	
}