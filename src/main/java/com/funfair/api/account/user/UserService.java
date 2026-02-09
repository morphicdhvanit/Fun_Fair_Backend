package com.funfair.api.account.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.funfair.api.account.device.AddDeviceDetailsDto;
import com.funfair.api.account.device.DeviceRepository;
import com.funfair.api.account.role.RoleRepository;
import com.funfair.api.common.OtpCache;
import com.funfair.api.common.Util;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.organizer.OrganizerRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Value("${jwt.secret}")
	private String jwtSecret;
	@Autowired
	Util util;
	@Autowired
	OtpCache otpCache;
	@Autowired
	RoleRepository RoleRepository;
	@Autowired
	OrganizerRepository orgRepository;
	@Autowired
	DeviceRepository deviceRepository;

	private String generateOtp() {
		return String.format("%06d", new Random().nextInt(1000000));
	}

	public UserLoginDto reSendOtp(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			throw new BadRequestException("Phone number is required");
		}

		// Check if user exists
		User user = userRepository.findByPhoneNo(phoneNumber);
		if (user == null) {
			throw new BadRequestException("User not found");
		}

		// Generate new OTP
		String otp = generateOtp();

		// Save OTP and update timestamp
		user.setOtpSend(otp);
		user.setUpdatedOn(LocalDateTime.now());
		userRepository.save(user);

		// Send OTP (or store in cache)
		// sendOtp.sendOtp(phoneNumber, otp);
		OtpCache.putOtp(phoneNumber, otp);

		return new UserLoginDto(phoneNumber, otp);
	}

	public List<UserDetailsDto> getAllUser() {
		List<User> users = userRepository.findAll();
		List<UserDetailsDto> dtos = new ArrayList<UserDetailsDto>();
		for (User user : users) {
			UserDetailsDto dto = convertUserEntityToDto(user);
			dtos.add(dto);
		}
		return dtos;
	}

	private UserDetailsDto convertUserEntityToDto(User user) {
		UserDetailsDto dto = new UserDetailsDto();
		dto.setCreatedBy(user.getCreatedBy());
		dto.setEmailId(user.getEmailId());
		dto.setGeneratedToken(user.getGeneratedToken());
		dto.setPhoneNo(user.getPhoneNo());
		dto.setUserId(user.getUserId());
		dto.setUserImage(user.getUserImage());
		dto.setUserName(user.getUserName());
		return dto;
	}

	public UserDetailsDto getUserDetailsByPhoneNumber(String phoneNumber) {
		System.out.println(phoneNumber);
		User user = userRepository.findByPhoneNo(phoneNumber);
		if (user == null) {
			throw new BadRequestException("User not found");
		}
		UserDetailsDto dto = convertUserEntityToDto(user);
		return dto;
	}

	public UserDetailsDto getUserDetailsById(String userId) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new BadRequestException("User not found");
		}
		UserDetailsDto dto = convertUserEntityToDto(user);
		return dto;
	}

}
