package com.funfair.api.organizer;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/organizer/org")
@CrossOrigin("*")
public class organizerController {
	@Value("${app.upload.user-image-path}")
	private String userImagePath;
	
	@Autowired
	OrganizerService organizerService;
	
	@GetMapping("/all")
	public ResponseEntity<List<OrganizerDetailsDto>> getAllOrganizer (){
		List<OrganizerDetailsDto> response =  organizerService.getAllOrganizer();
				return new ResponseEntity<List<OrganizerDetailsDto>>(response, HttpStatus.OK);
	}
	@GetMapping("/{orgId}")
	public ResponseEntity<OrganizerDetailsDto> getOrganizerByOrgId(@PathVariable String orgId) {
	    OrganizerDetailsDto response = organizerService.getOrganizerByOrgId(orgId);
	    return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	@PatchMapping("/update/{orgId}")
	public ResponseEntity<OrganizerDetails> updateOrganizerDetails(@PathVariable String orgId , @RequestBody UpdateOrganizerDetailsDto dto) throws IOException{
		OrganizerDetails response = organizerService.updateOrganizerDetails(orgId , dto , userImagePath);
		return new ResponseEntity<OrganizerDetails>(response, HttpStatus.OK);
	}

	

}
