package com.funfair.api.event.salespersonticketdetails;

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
@Table(name = "event_salesperson_ticket_details")
public class SalespersonTicketDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "event_id", nullable = false)
	private String eventId;
	@Column(name = "salesperson_user_id", nullable = false)
	private String salespersonId;
	@Column(name = "ticket_type_id", nullable = false)
	private String ticketTypeId;
	@Column(name = "total_tickets_quantity")
	private int totalTicketsQuantity;
	@Column(name = "tickets_sold_quantity")
	private int ticketsSoldQuantity;
	@Column(name = "tickets_available_quantity")
	private int ticketsAvailableQuantity;
	
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
