package com.funfair.api.organizer.organizerrequest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("organizer/request")
@CrossOrigin("*")
public class OrganizerRequestDetailsCotroller {
	@Value("${app.upload.user-image-path}")
	private String userImagePath;
	
	@Autowired
	OrganizerRequestDetailsService organizerService;
	
	@PostMapping("/add")
	public ResponseEntity<OrganizerRequestDetails> addOrganizerRequest(@ModelAttribute AddOrganizerRequestDto addOrganizerRequestDto) throws IOException{
		OrganizerRequestDetails response = organizerService.addOrganizerRequest(addOrganizerRequestDto ,userImagePath );
		return new ResponseEntity<OrganizerRequestDetails>(response, HttpStatus.OK);	
		}
	@GetMapping("/")
	public ResponseEntity<List<AllOrganizerRequestDto>> getAllOrganizerRequestDetails (){
		List<AllOrganizerRequestDto> response = organizerService.getAllOrgazerRequestDetails();
		return new ResponseEntity<List<AllOrganizerRequestDto>>(response, HttpStatus.OK);
	}
	@GetMapping("/by-id/{organizerRequestId}")
	public ResponseEntity<AllOrganizerRequestDto> getOrganizationRequestDetailsById (@PathVariable String organizerRequestId){
		AllOrganizerRequestDto response = organizerService.getOrganizerRequestDetailsbyId(organizerRequestId);
		return new ResponseEntity<AllOrganizerRequestDto>(response, HttpStatus.OK);
	}
	@PostMapping("/update-Request-status/{organizerRequestId}")
	public ResponseEntity<OrganizerRequestDetails> updateRequestStatus(@PathVariable String organizerRequestId , @RequestParam String requestStatus){
		OrganizerRequestDetails response = organizerService.updateRequestStatus(organizerRequestId , requestStatus);
		return new ResponseEntity<OrganizerRequestDetails>(response, HttpStatus.OK);
	}
	
	
}



