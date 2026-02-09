package com.funfair.api.organizer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.account.user.User;
import com.funfair.api.account.user.UserRepository;
import com.funfair.api.common.Util;
import com.funfair.api.exception.BadRequestException;

import jakarta.transaction.Transactional;

@Service
public class OrganizerService {
	
	@Autowired
	OrganizerRepository organizerRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	Util util;

	public List<OrganizerDetailsDto> getAllOrganizer() {
		List<OrganizerDetails> details = organizerRepository.findAll();
		List<OrganizerDetailsDto> dtos = new ArrayList<OrganizerDetailsDto>();
		for (OrganizerDetails organizerDetails : details) {
			OrganizerDetailsDto dto  = convertOrganizerDetailsEntityToDto(organizerDetails);
			dtos.add(dto);
			
		}
		return dtos;
	}

	private OrganizerDetailsDto convertOrganizerDetailsEntityToDto(OrganizerDetails organizerDetails) {
		OrganizerDetailsDto dto = new OrganizerDetailsDto();
		dto.setOrgId(organizerDetails.getOrgId());
		dto.setOrgName(organizerDetails.getOrgName());
		dto.setOrgNumber(organizerDetails.getOrgNumber());
		dto.setOrgtEmailId(organizerDetails.getOrgtEmailId());
		dto.setOrgUserId(organizerDetails.getOrgUserId());
		return dto;
	}
	public OrganizerDetailsDto getOrganizerByOrgId(String orgId) {

	    OrganizerDetails organizerDetails = organizerRepository.findByOrgId(orgId);
	    if (organizerDetails == null) {
			throw new BadRequestException("organizer Details not found");
		}
	    return convertOrganizerDetailsEntityToDto(organizerDetails);
	}

	@Transactional
	public OrganizerDetails updateOrganizerDetails(String orgId, UpdateOrganizerDetailsDto dto , String UserImagePath) throws IOException {

	    // 1. Fetch Organizer
	    OrganizerDetails organizer = organizerRepository.findByOrgId(orgId);
	    if (organizer == null) {
	        throw new BadRequestException("Organizer not found with orgId: " + orgId);
	    }

	    // 2. Update OrganizerDetails
	    if (dto.getOrgName() != null) {
	        organizer.setOrgName(dto.getOrgName());
	    }
	    if (dto.getOrgNumber() != null) {
	        organizer.setOrgNumber(dto.getOrgNumber());
	    }
	    if (dto.getOrgtEmailId() != null) {
	        organizer.setOrgtEmailId(dto.getOrgtEmailId());
	    }

	    organizer.setUpdatedOn(LocalDateTime.now());
	    organizer.setUpdatedBy(dto.getOrgName());

	    organizerRepository.save(organizer);

	    // 3. Fetch User
	    User user = userRepository.findByUserId(organizer.getOrgUserId());
	    if (user == null) {
	        throw new BadRequestException("User not found for organizer");
	    }

	    // 4. Update User fields
	    if (dto.getOrgName() != null) {
	        user.setUserName(dto.getOrgName());
	    }
	    if (dto.getOrgNumber() != null) {
	        user.setPhoneNo(dto.getOrgNumber());
	    }
	    if (dto.getOrgtEmailId() != null) {
	        user.setEmailId(dto.getOrgtEmailId());
	    }

	    // 5. Handle Image Upload
	    if (dto.getUserImage() != null && !dto.getUserImage().isEmpty()) {
	    	util.deleteFile( UserImagePath, user.getUserImage());
	        String imagePath = util.saveFile(dto.getUserImage(),UserImagePath);

	        user.setUserImage(imagePath);
	    }

	    user.setUpdatedBy(dto.getOrgName());
	    user.setUpdatedOn(LocalDateTime.now());

	    userRepository.save(user);

	    return organizer;
	}
	
    public OrganizerDetails validateOrganizer(String organizerId) {
    	System.out.println("Validating Organizer ID: " + organizerId);
        OrganizerDetails organizer = organizerRepository.findByOrgId(organizerId);
        if (organizer == null || !organizer.isActive()) {
            throw new BadRequestException("Invalid or inactive Organizer ID: " + organizerId);
        }
        return organizer;
    }

	

}
