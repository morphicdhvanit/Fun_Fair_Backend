package com.funfair.api.event.gatenodetails;

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
@Entity()
@Table(name = "event_gate_no_details")
public class GateNoDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "event_id")
	private String eventId;
	@Column(name = "gate_no")
	private String gateNo;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "org_id")
	private String orgId;
	@Column(name = "gate_no_date")
	private LocalDateTime gateNoDate;
	@Column(name = "door_manage_gate_number_id")
	private String doorManageGateNumberId;

	@Column(name = "is_active")
	private boolean isActive = true;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

}
