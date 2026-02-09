package com.funfair.api.account.userrole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.funfair.api.account.user.UserLoginDto;

@RestController
@RequestMapping("/account/user-role")
public class UserRoleController {

	@Autowired
	UserRoleService userRoleService;

	@PostMapping("/send-otp")
	public ResponseEntity<UserLoginDto> sendOtpToSalesPerson(@RequestBody SendOtpMasterDto sendOtpMasterDto) {
		UserLoginDto response = userRoleService.sendOtpByRole(sendOtpMasterDto);
		return new ResponseEntity<UserLoginDto>(response, HttpStatus.OK);
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<VerifyOtpResponseDto> verifyOtp(@RequestBody VerifyOtpRequestDto request) {
		VerifyOtpResponseDto response = userRoleService.verifyOtpByRole(request);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/all-sales-persons/{orgId}")
	public ResponseEntity<List<SalesPersonListDto>> getAllSalesPersons(String orgId) {
		List<SalesPersonListDto> response = userRoleService.getAllSalesPersons(orgId);
		return ResponseEntity.ok(response);
	}
	

}
