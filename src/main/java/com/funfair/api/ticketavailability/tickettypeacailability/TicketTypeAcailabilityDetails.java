package com.funfair.api.ticketavailability.tickettypeacailability;

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
@Table(name = "ticket_type_avilability_details")
public class TicketTypeAcailabilityDetails {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "org_id", nullable = false)
    private String orgId;

    @Column(name = "ticket_type_id", nullable = false)
    private String ticketTypeId;

    @Column(name = "total_available_ticket")
    private int totalAvailableTicket;
    
    @Column(name  = "ticket_type_name")
    private String ticketTypeName;

    @Column(name = "total_freezed_ticket")
    private int totalFreezedTicket;

    @Column(name = "total_sold_ticket")
    private int totalSoldTicket;
    
    @Column(name  = "ticket_price")
    private double ticketPrice;

    
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
