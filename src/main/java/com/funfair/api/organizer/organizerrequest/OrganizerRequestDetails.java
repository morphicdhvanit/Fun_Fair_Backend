package com.funfair.api.organizer.organizerrequest;

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
@Table(name = "organizer_request_details")
public class OrganizerRequestDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column(name  = "organizer_request_id")
	private String organizerRequestId;
	@Column(name = "organizer_name")
	private String organizerName;
	@Column(name = "organizer_number")
	private String organizernumber;
	@Column(name  = "organizer_emailId")
	private String organizerEmailId;
	@Column(name  = "organizer_image-path")
	private String organizerImagePath;
	@Column(name  = "organizer_event_category")
	private String organizerEventCategory;
	@Column (name  = "organizer_event_title")
	private String organizerEventTitle;
	@Column(name = "organizer_city")
	private String organizerCity;
	
	@Column(name = "organizer_short_dec")
	private String organizerShortDec;
	@Column(name  = "organizer_request_status")
	@Enumerated(EnumType.STRING)
	private OrganizerRequestStatusEnum organizerRequestStatus;
	
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
