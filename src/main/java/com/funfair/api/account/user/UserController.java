package com.funfair.api.account.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funfair.api.account.device.AddDeviceDetailsDto;


@RestController
@RequestMapping("/account/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
    @PostMapping("/resend-otp/{phoneNumber}")
    public ResponseEntity<UserLoginDto> reSendOtp(@PathVariable String phoneNumber) {
        UserLoginDto response = userService.reSendOtp(phoneNumber);
        return new ResponseEntity<UserLoginDto>(response, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDetailsDto>> getAllUser(){
    	List<UserDetailsDto> response  = userService.getAllUser();
    	return new ResponseEntity<List<UserDetailsDto>>(response, HttpStatus.OK);
    }
    @GetMapping("/by-phone-number/{phoneNumber}")
    public ResponseEntity<UserDetailsDto> getUserDetailsByPhoneNumber(@PathVariable String phoneNumber){
    	UserDetailsDto response  = userService.getUserDetailsByPhoneNumber(phoneNumber);
    	return new ResponseEntity<UserDetailsDto>(response, HttpStatus.OK);
    }
    @GetMapping("/by-id/{userId}")
    public ResponseEntity<UserDetailsDto> getUserDetailsById(@PathVariable String userId){
    	UserDetailsDto response  = userService.getUserDetailsById(userId);
    	return new ResponseEntity<UserDetailsDto>(response, HttpStatus.OK);
    }

}
