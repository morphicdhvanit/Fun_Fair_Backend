package com.funfair.api.organizer.organizerrequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.account.role.Role;
import com.funfair.api.account.role.RoleRepository;
import com.funfair.api.account.user.User;
import com.funfair.api.account.user.UserRepository;
import com.funfair.api.account.userrole.UserRole;
import com.funfair.api.account.userrole.UserRoleRepository;
import com.funfair.api.common.ImagePathUrl;
import com.funfair.api.common.Util;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.organizer.OrganizerDetails;
import com.funfair.api.organizer.OrganizerRepository;

import jakarta.transaction.Transactional;

@Service
public class OrganizerRequestDetailsService {

	
	@Autowired
	OrganizerRepository organizerRepository;
	@Autowired
	OrganizerRequestRepository	organizerRequestRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRoleRepository userRoleRepository;

    @Autowired
    private Util util;

    public OrganizerRequestDetails addOrganizerRequest(AddOrganizerRequestDto dto , String userImagePath) throws IOException {

        // Validate email & phone
        if (!util.isEmailValid(dto.getOrganizerEmailId())) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (!util.isPhoneValid(dto.getOrganizernumber())) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        OrganizerRequestDetails organizerRequestDetails = new OrganizerRequestDetails();

        organizerRequestDetails.setOrganizerRequestId(util.getIdFromName(dto.getOrganizerName()));
        organizerRequestDetails.setOrganizerName(dto.getOrganizerName());
        organizerRequestDetails.setOrganizerEmailId(dto.getOrganizerEmailId());
        organizerRequestDetails.setOrganizernumber(dto.getOrganizernumber());
        organizerRequestDetails.setOrganizerCity(dto.getOrganizerCity());
        organizerRequestDetails.setOrganizerEventCategory(dto.getOrganizerEventCategory());
        organizerRequestDetails.setOrganizerEventTitle(dto.getOrganizerEventTitle());
        organizerRequestDetails.setOrganizerShortDec(dto.getOrganizerShortDec());
        organizerRequestDetails.setCreatedBy(dto.getOrganizerName());
        organizerRequestDetails.setOrganizerRequestStatus(OrganizerRequestStatusEnum.IN_PROGRESS);
        organizerRequestDetails.setCreatedOn(LocalDateTime.now());
        if (dto.getUserImage() != null && !dto.getUserImage().isEmpty()) {
            String filepath =  util.saveFile(dto.getUserImage(), userImagePath);
            organizerRequestDetails.setOrganizerImagePath(filepath);
        }
        OrganizerRequestDetails details =  organizerRequestRepository.save(organizerRequestDetails);
        return details; 
    }

    private AllOrganizerRequestDto convertToDto(OrganizerRequestDetails entity) {

        AllOrganizerRequestDto dto = new AllOrganizerRequestDto();
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setOrganizerCity(entity.getOrganizerCity());
        dto.setOrganizerEmailId(entity.getOrganizerEmailId());
        dto.setOrganizerEventCategory(entity.getOrganizerEventCategory());
        dto.setOrganizerEventTitle(entity.getOrganizerEventTitle());
        dto.setOrganizerImagePath( ImagePathUrl.USER_IMAGE_BASE_URL + entity.getOrganizerImagePath());
        dto.setOrganizerName(entity.getOrganizerName());
        dto.setOrganizernumber(entity.getOrganizernumber());
        dto.setOrganizerRequestId(entity.getOrganizerRequestId());
        dto.setOrganizerRequestStatus(
                entity.getOrganizerRequestStatus().getName()
        );
        dto.setOrganizerShortDec(entity.getOrganizerShortDec());

        return dto;
    }
	public List<AllOrganizerRequestDto> getAllOrgazerRequestDetails() {
		
		List<OrganizerRequestDetails> details  =  organizerRequestRepository.findAll();
		List<AllOrganizerRequestDto> dtos = new ArrayList<>();
		for (OrganizerRequestDetails organizerRequestDetails : details) {
			dtos.add(convertToDto(organizerRequestDetails));
		}
		return dtos;
	}

	public AllOrganizerRequestDto getOrganizerRequestDetailsbyId(String organizerRequestId) {
		OrganizerRequestDetails details = organizerRequestRepository.findByOrganizerRequestId(organizerRequestId);
		AllOrganizerRequestDto dto = convertToDto(details);
		return dto;
	}

	@Transactional
	public OrganizerRequestDetails updateRequestStatus(
	        String organizerRequestId,
	        String requestStatus) {

	    OrganizerRequestDetails details =
	            organizerRequestRepository.findByOrganizerRequestId(organizerRequestId);

	    if (details == null) {
	        throw new RuntimeException("Organizer request not found with id: " + organizerRequestId);
	    }

	    OrganizerRequestStatusEnum statusEnum = getStatusEnum(requestStatus);

	    // If already approved, do not re-process
	    if (details.getOrganizerRequestStatus() == OrganizerRequestStatusEnum.APPROVED) {
	        throw new BadRequestException("Request already approved");
	    }

	    // APPROVE FLOW
	    if (statusEnum == OrganizerRequestStatusEnum.APPROVED) {
	        approveOrganizer(details);
	    }

	    details.setOrganizerRequestStatus(statusEnum);
	    details.setUpdatedOn(LocalDateTime.now());

	    return organizerRequestRepository.save(details);
	}
	private OrganizerRequestStatusEnum getStatusEnum(String requestStatus) {

	    for (OrganizerRequestStatusEnum status : OrganizerRequestStatusEnum.values()) {
	        if (status.getName().equalsIgnoreCase(requestStatus)) {
	            return status;
	        }
	    }

	    throw new IllegalArgumentException(
	            "Invalid request status: " + requestStatus +
	            ". Allowed values: in_progress, approved, rejected"
	    );
	}
	private void approveOrganizer(OrganizerRequestDetails request) {

	    LocalDateTime now = LocalDateTime.now();

	    
//	    add user Details 
	    User user = new User();
	    user.setUserId(util.generateRandomNumericId());
	    user.setUserName(request.getOrganizerName());
	    user.setEmailId(request.getOrganizerEmailId());
	    user.setPhoneNo(request.getOrganizernumber());
	    user.setUserImage(request.getOrganizerImagePath());
	    user.setCreatedBy(request.getOrganizerName());
	    user.setCreatedOn(now);
	    user = userRepository.save(user);
	    

//	    add userRole Details 
	    Role organizerRole = roleRepository.findByRoleName("ORGANIZER");

	    UserRole userRole = new UserRole();
	    userRole.setUser(user);
	    userRole.setRole(organizerRole);
	    userRole.setOrgId(request.getOrganizerRequestId());
	    userRole.setCreatedBy(user.getUserName());
	    userRole.setCreatedOn(now);
	    userRoleRepository.save(userRole);
	    
//	    add organierDetials 
	    OrganizerDetails organizerDetails = new OrganizerDetails();
	    organizerDetails.setOrgId(util.generateRandomNumericId());
	    organizerDetails.setOrgName(request.getOrganizerName());
	    organizerDetails.setOrgtEmailId(request.getOrganizerEmailId());
	    organizerDetails.setOrgNumber(request.getOrganizernumber());
	    organizerDetails.setOrgUserId(user.getUserId());
	    organizerDetails.setCreatedBy(request.getOrganizerName());
	    organizerDetails.setCreatedOn(now);

	    organizerRepository.save(organizerDetails);
	}
}

