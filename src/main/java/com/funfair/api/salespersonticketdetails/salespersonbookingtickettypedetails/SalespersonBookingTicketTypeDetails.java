package com.funfair.api.salespersonticketdetails.salespersonbookingtickettypedetails;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name  = "salesperson_booking_ticket_type_details")
public class SalespersonBookingTicketTypeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "salesperson_id", nullable = false)
	private String salespersonId;
	@Column(name = "event_id", nullable = false)
	private String eventId;
	@Column(name  = "organizer_id", nullable = false)
	private String organizerId;
	@Column(name = "ticket_number", unique = true)
	private String ticketNumber;
	@Column(name  = "ticket_type_id")
	private String ticketTypeId;
	@Column(name  = "ticket_quantity")
	private int ticketQuantity;
	@Column(name  = "one_ticket_price")
	private double oneTicketPrice;
	
	@Column(name  = "total_ticket_price")
	private double totalTicketPrice;
	
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
