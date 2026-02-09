package com.funfair.api.organizer;

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
@Table(name ="organizer_org_details")
public class OrganizerDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(name= "org_id")
	private String orgId;
	@Column(name = "org_name")
	private String orgName;
	@Column (name = "org_emailId")
	private String orgtEmailId;
	@Column(name  = "org_number" , unique = true)
	private String orgNumber;
	@Column(name = "org_user_id")
	private String orgUserId;
	
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
