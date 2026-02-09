package com.funfair.api.account.user;

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
@Entity
@Table(name = "account_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id", unique = true)
	private String userId;
	@Column(name = "user_name")
	private String userName;
	@Column(name  = "user_image")
	private String userImage;
	@Column(name = "phone_no")
	private String phoneNo;
	@Column(name = "email_id", unique = true)
	private String emailId;
	@Column(name = "otp_send")
	private String otpSend;
	@Column(name  = "generated_token")
	public String generatedToken;
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
