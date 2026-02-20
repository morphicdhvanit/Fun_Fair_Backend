package com.funfair.api.salespersonticketdetails;

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
@Table(name  = "salesperson_ticket_booking_details")
public class SalespersonTicketBookingDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "salesperson_id", nullable = false)
	private String salespersonId;
	@Column(name = "event_id", nullable = false)
	private String eventId;
	@Column(name  = "organizer_id", nullable = false)
	private String organizerId;
	@Column(name  = "buyer_name")
	private String buyerName;
	@Column(name  = "buyer_phone_number")
	private String buyerPhoneNumber;
	@Column(name  = "ticket_qr_token", unique = true, nullable = false, length = 100)
	private String ticketQrToken;
	@Column(name  = "payment_method")
	@Enumerated(EnumType.STRING)
	private PaymnetMethodTypeEnum paymentMethod;
	@Column(name  = "total_payment_amount")
	private double totalPaymentAmount;
	@Column(name = "is_ticket_used")
	private boolean isTicketUsed = false;
	@Column(name = "ticket_number", unique = true)
	private String ticketNumber;
	@Column(name = "ticket_used_on")
	private LocalDateTime ticketUsedOn;
	@Column(name = "total_quantity")
	private int Totalquantity;
	@Column(name  = "ticket_status")
	@Enumerated(EnumType.STRING)
	private TicketStatus ticketStatus;
	@Column(name  = "is_payment_successful")
	private boolean isPaymentSuccessful = false;
	
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
