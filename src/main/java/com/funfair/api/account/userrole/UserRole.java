package com.funfair.api.account.userrole;


import java.time.LocalDateTime;

import com.funfair.api.account.role.Role;
import com.funfair.api.account.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_user_role")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne()
	@JoinColumn(name = "role_id")
	public Role role;
	@Column(name =  "organization_id")
	private String orgId;
	@Column(name = "event_id")
	private String eventId;
	@Column(name  = "role_start_date_time")
	private LocalDateTime roleStartDateTime;
	@Column(name  = "role_end_date_time")
	private LocalDateTime roleEndDateTime;
	
	
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
