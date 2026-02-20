package com.funfair.api.ticketavailability;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_availability_details")
public class TicketAvailabilityDetails {
	
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "event_id")
	private String eventId;
	@Column(name = "org_id")
	private String orgId;
	@Column(name = "total_tickets")
	private int totalTickets;
	@Column(name = "available_tickets")
	private int availableTickets;
	@Column(name = "sold_tickets")
	private int soldTickets;
	@Column(name = "freezed_tickets")
	private int freezedTickets;
	@Column(name  = "booking_price")
	private double bookingPrice;
	
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
