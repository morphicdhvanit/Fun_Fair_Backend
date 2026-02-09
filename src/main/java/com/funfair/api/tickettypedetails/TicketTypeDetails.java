package com.funfair.api.tickettypedetails;

import java.time.LocalDateTime;

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
@Table(name  = "ticket_type_details")
public class TicketTypeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name  = "ticket_type_id")
	private String 	ticketTypeId;
	@Column(name  = "ticket_name")
	private String ticketName;
	@Column(name  = "quntity_avialable")
	private int quntityAvialable;
	@Column(name  = "ticket_price")
	private double ticketPrice;
	@Column(name  = "ticket_dec")
	private String ticketDec;
	@Column(name  = "org_id")
	private String orgId;
	@Column(name  = "event_id")
	private String eventId;
	
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
