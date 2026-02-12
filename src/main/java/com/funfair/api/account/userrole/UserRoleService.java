package com.funfair.api.account.userrole;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.funfair.api.account.device.AddDeviceDetailsDto;
import com.funfair.api.account.device.DeviceDetails;
import com.funfair.api.account.device.DeviceRepository;
import com.funfair.api.account.role.Role;
import com.funfair.api.account.role.RoleService;
import com.funfair.api.account.role.RoleType;
import com.funfair.api.account.user.User;
import com.funfair.api.account.user.UserLoginDto;
import com.funfair.api.account.user.UserRepository;
import com.funfair.api.common.ImagePathUrl;
import com.funfair.api.common.OtpCache;
import com.funfair.api.common.Util;
import com.funfair.api.event.EventDetails;
import com.funfair.api.event.gatenodetails.DoorGateDetailsDto;
import com.funfair.api.event.gatenodetails.GateNoDetails;
import com.funfair.api.event.gatenodetails.GateNoDetailsRepository;
import com.funfair.api.event.salespersonticketdetails.SalesPersonTicketDetailsDto;
import com.funfair.api.event.salespersonticketdetails.SalespersonTicketService;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.organizer.OrganizerDetails;
import com.funfair.api.organizer.OrganizerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;

@Service
public class UserRoleService {

	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrganizerRepository organizerRepository;
	@Autowired
	DeviceRepository deviceRepository;
	@Autowired
	SalespersonTicketService salespersonTicketService;
	@Autowired
	RoleService roleService;
	@Value("${jwt.secret}")
	private String jwtSecret;
	@Autowired
	GateNoDetailsRepository gateNoDetailsRepository;
	@Autowired
	Util util;

	public void assignSalesPersonsToEvent(List<AddSalesPersonDetailsDto> salesPersons, EventDetails event,
			OrganizerDetails organizer) {
		System.out.println("inside");

		if (salesPersons == null || salesPersons.isEmpty()) {
			return;
		}

		for (AddSalesPersonDetailsDto dto : salesPersons) {
			User user = userRepository.findByPhoneNo(dto.getUserphoneNumber());
			System.out.println("user" + user);

			if (user == null) {
				user = createNewUser(dto);
				System.out.println("new user" + user);

			}

			assignSalesPersonRole(user, dto, event, organizer);
		}
	}

	public void assignDoorManagerToEvent(List<AddDoorManagerDto> DoorManagers, EventDetails event,
			OrganizerDetails organizer) {

		if (DoorManagers == null || DoorManagers.isEmpty()) {
			return;
		}

		for (AddDoorManagerDto dto : DoorManagers) {
			User user = userRepository.findByPhoneNo(dto.getUserphoneNumber());
			System.out.println("user" + user);

			if (user == null) {
				user = createNewUser(dto);
				System.out.println("new user" + user);

			}

			assignDoorManager(user, dto, event, organizer);
		}
	}

	private User createNewUser(AddDoorManagerDto dto) {
		System.out.println("createNewUser" + dto.getUserName());

		User user = new User();
		user.setUserId(util.generateRandomNumericId());
		user.setUserName(dto.getUserName());
		user.setPhoneNo(dto.getUserphoneNumber());
		user.setEmailId(dto.getUserEmail());
		user.setCreatedOn(LocalDateTime.now());

		return userRepository.save(user);
	}

	private User createNewUser(AddSalesPersonDetailsDto dto) {
		System.out.println("createNewUser" + dto.getUserName());

		User user = new User();
		user.setUserId(util.generateRandomNumericId());
		user.setUserName(dto.getUserName());
		user.setPhoneNo(dto.getUserphoneNumber());
		user.setEmailId(dto.getUserEmail());
		user.setCreatedOn(LocalDateTime.now());

		return userRepository.save(user);
	}

	private void assignSalesPersonRole(User user, AddSalesPersonDetailsDto dto, EventDetails event,
			OrganizerDetails organizer) {
		System.out.println("assignSalesPersonRole" + user.getUserName());

		Role role1 = roleService.getRoleById(RoleType.SALESPERSON);
		System.out.println("role1" + role1);
		boolean alreadyAssigned = userRoleRepository.existsByUserUserIdAndEventIdAndRoleRoleId(user.getUserId(),
				event.getEventId(), role1.getRoleId());
		System.out.println("alreadyAssigned" + alreadyAssigned);

		if (alreadyAssigned) {
			return;
		}

		UserRole role = new UserRole();
		role.setUser(user);
		role.setRole(roleService.getRoleById(RoleType.SALESPERSON));
		role.setOrgId(organizer.getOrgId());
		role.setEventId(event.getEventId());
		role.setRoleStartDateTime(event.getEventStartDateTime());
		role.setRoleEndDateTime(event.getEventEndDateTime());
		role.setCreatedBy(organizer.getOrgUserId());
		role.setCreatedOn(LocalDateTime.now());
		userRoleRepository.save(role);
	}

	private void assignDoorManager(User user, AddDoorManagerDto dto, EventDetails event, OrganizerDetails organizer) {

		Role role1 = roleService.getRoleById(RoleType.DOORMANAGER);
		System.out.println("role1" + role1);
		boolean alreadyAssigned = userRoleRepository.existsByUserUserIdAndEventIdAndRoleRoleId(user.getUserId(),
				event.getEventId(), role1.getRoleId());
		System.out.println("alreadyAssigned" + alreadyAssigned);

		if (alreadyAssigned) {
			return;
		}

		UserRole role = new UserRole();
		role.setUser(user);
		role.setRole(roleService.getRoleById(RoleType.DOORMANAGER));
		role.setOrgId(organizer.getOrgId());
		role.setEventId(event.getEventId());
		role.setRoleStartDateTime(event.getEventStartDateTime());
		role.setRoleEndDateTime(event.getEventEndDateTime());
		role.setCreatedBy(organizer.getOrgUserId());
		role.setCreatedOn(LocalDateTime.now());
		userRoleRepository.save(role);
	}

	public List<GetDoorManagerDetailsDto> getDoorManagersByEventId(String eventId, String orgId) {

	    List<GateNoDetails> gateList =
	            gateNoDetailsRepository.findByEventIdAndOrgId(eventId, orgId);

	    List<UserRole> userRoles =
				userRoleRepository.findByEventIdAndRoleRoleIdAndOrgId(
	                    eventId, RoleType.DOORMANAGER, orgId
	            );

	    Map<String, UserRole> userRoleMap = userRoles.stream()
	            .collect(Collectors.toMap(
	                    ur -> ur.getUser().getUserId(),
	                    ur -> ur
	            ));

	    Map<String, List<GateNoDetails>> gatesByUser =
	            gateList.stream().collect(Collectors.groupingBy(GateNoDetails::getUserId));

	    List<GetDoorManagerDetailsDto> response = new ArrayList<>();

	    for (String userId : gatesByUser.keySet()) {

	        UserRole userRole = userRoleMap.get(userId);
	        if (userRole == null) continue;

	        GetDoorManagerDetailsDto dto = new GetDoorManagerDetailsDto();
	        dto.setUserId(userId);
	        dto.setUserName(userRole.getUser().getUserName());
	        dto.setUserEmail(userRole.getUser().getEmailId());
	        dto.setUserPhoneNumber(userRole.getUser().getPhoneNo());
	        dto.setUserImage(ImagePathUrl.USER_IMAGE_BASE_URL + userRole.getUser().getUserImage());
	        dto.setRoleStartDateTime(userRole.getRoleStartDateTime());
	        dto.setRoleEndDateTime(userRole.getRoleEndDateTime());
	        dto.setEventId(eventId);
	        dto.setOrgId(orgId);

	        List<DoorGateDetailsDto> gateDtos = gatesByUser.get(userId)
	                .stream()
	                .map(g -> new DoorGateDetailsDto(
	                        g.getDoorManageGateNumberId(),
	                        g.getGateNo(),
	                        g.getGateNoDate()
	                ))
	                .toList();

	        dto.setGates(gateDtos);
	        response.add(dto);
	    }

	    return response;
	}


	public List<GetSalsePersonDetailsDto> getSalesPersonsByEventId(String eventId) {
		List<UserRole> userRoles = userRoleRepository.findByEventIdAndRoleRoleId(eventId, RoleType.SALESPERSON);
		List<GetSalsePersonDetailsDto> dtos = new ArrayList<GetSalsePersonDetailsDto>();
		for (UserRole userRole : userRoles) {

			List<SalesPersonTicketDetailsDto> ticketDetails = salespersonTicketService.getSalesPersonTicketDetails(eventId, userRole.getUser().getUserId());
			GetSalsePersonDetailsDto dto = new GetSalsePersonDetailsDto();
			dto.setEventId(userRole.getEventId());
			dto.setOrgid(userRole.getOrgId());
			dto.setUserId(userRole.getUser().getUserId());
			dto.setRoleStartDateTime(userRole.getRoleStartDateTime());
			dto.setRoleEndDateTime(userRole.getRoleEndDateTime());
			dto.setUserEmail(userRole.getUser().getEmailId());
			dto.setUserImage(userRole.getUser().getUserImage());
			dto.setUserName(userRole.getUser().getUserName());
			dto.setUserphoneNumber(userRole.getUser().getPhoneNo());
			dto.setSalesPersonTicketDetails(ticketDetails);
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional
	public UserLoginDto sendOtpByRole(SendOtpMasterDto dto) {

	    // 1️ Basic Validation
	    if (dto.getPhoneNumber() == null || dto.getPhoneNumber().isEmpty()) {
	        throw new BadRequestException("Phone number is required");
	    }

	    if (dto.getRoleName() == null || dto.getRoleName().isEmpty()) {
	        throw new BadRequestException("Role is required");
	    }

	    // 2️ Resolve Role
	    RoleType roleType = roleService.getRoleType(dto.getRoleName());

	    // 3️ Fetch User
	    User user = userRepository.findByPhoneNo(dto.getPhoneNumber());

	    // CUSTOMER AUTO-CREATION LOGIC
	    if (user == null) {

	        if (roleType != RoleType.CUSTOMER) {
	            throw new BadRequestException("User not registered");
	        }

	        // Create CUSTOMER user
	        user = createCustomerUser(dto);
	        createCustomerRole(user);
	    }

	    // 4️ Validate User Role
	    UserRole userRole =
	            userRoleRepository.findByUserUserIdAndRoleRoleId(
	                    user.getUserId(), roleType
	            );

	    if (userRole == null || !Boolean.TRUE.equals(userRole.isActive())) {
	        throw new BadRequestException("User does not have required role");
	    }

	    // 5️ Time-based Role Validation
	    if (roleType == RoleType.SALESPERSON || roleType == RoleType.DOORMANAGER) {

	        LocalDateTime now = LocalDateTime.now();

	        if (userRole.getRoleStartDateTime() != null &&
	            userRole.getRoleEndDateTime() != null) {

	            if (now.isBefore(userRole.getRoleStartDateTime()) ||
	                now.isAfter(userRole.getRoleEndDateTime())) {

	                throw new BadRequestException(
	                        "Login not allowed for this role at the current time"
	                );
	            }
	        }
	    }

	    // 6️ Generate OTP
	    String otp = generateOtp();
	    user.setOtpSend(otp);
	    user.setUpdatedOn(LocalDateTime.now());
	    userRepository.save(user);

	    // 7️ Save / Update Device
	    AddDeviceDetailsDto deviceDto = new AddDeviceDetailsDto();
	    deviceDto.setPhoneNumber(dto.getPhoneNumber());
	    deviceDto.setDeviceId(dto.getDeviceId());
	    deviceDto.setDeviceToken(dto.getDeviceToken());
	    deviceDto.setDeviceType(dto.getDeviceType());

	    saveOrUpdateDevice(user, deviceDto);

	    // 8️ Cache & Send OTP
	    OtpCache.putOtp(dto.getPhoneNumber(), otp);
	    // sendOtp.sendOtp(dto.getPhoneNumber(), otp);

	    return new UserLoginDto(dto.getPhoneNumber(), otp);
	}

	private User createCustomerUser(SendOtpMasterDto dto) {

	    User user = new User();
	    user.setPhoneNo(dto.getPhoneNumber());
	    user.setCreatedOn(LocalDateTime.now());
	    return userRepository.save(user);
	}

	private void createCustomerRole(User user) {

		Role role = roleService.getRoleById(RoleType.CUSTOMER);
	    UserRole userRole = new UserRole();
	    userRole.setUser(user);
	    userRole.setRole(role);
	    userRole.setCreatedOn(LocalDateTime.now());

	    userRoleRepository.save(userRole);
	}

	private String generateOtp() {
		return String.format("%06d", new Random().nextInt(1000000));
	}

	private void saveOrUpdateDevice(User user, AddDeviceDetailsDto dto) {

		DeviceDetails device = deviceRepository.findByUserId(user.getUserId());

		if (device == null) {
			device = new DeviceDetails();
			device.setUserId(user.getUserId());
			device.setDeviceId(dto.getDeviceId());
			device.setDeviceToken(dto.getDeviceToken());
			device.setDeviceType(dto.getDeviceType());
			device.setCreatedOn(LocalDateTime.now());
		} else {
			device.setDeviceToken(dto.getDeviceToken());
			device.setUpdatedOn(LocalDateTime.now());
		}

		deviceRepository.save(device);
	}

	public VerifyOtpResponseDto verifyOtpByRole(VerifyOtpRequestDto dto) {

		// 1️⃣ Validate Request
		if (dto.getPhoneNumber() == null || dto.getPhoneNumber().isEmpty()) {
			throw new BadRequestException("Phone number is required");
		}

		if (dto.getOtp() == null    || dto.getOtp().isEmpty()) {
			throw new BadRequestException("OTP is required");
		}

		if (dto.getRoleName() == null || dto.getRoleName().isEmpty()) {
			throw new BadRequestException("Role is required");
		}

		// 2️ Fetch User
		User user = userRepository.findByPhoneNo(dto.getPhoneNumber());
		if (user == null) {
			throw new BadRequestException("User not found");
		}

		// 3️ Validate OTP (Cache or DB)
		String cachedOtp = OtpCache.getOtp(dto.getPhoneNumber());

		if (cachedOtp == null || !cachedOtp.equals(dto.getOtp())) {
			throw new BadRequestException("Invalid or expired OTP");
		}

		// Optional DB check
		if (!dto.getOtp().equals(user.getOtpSend())) {
			throw new BadRequestException("Invalid OTP");
		}

		// 4️ Resolve Role
		RoleType roleType = roleService.getRoleType(dto.getRoleName());
		UserRole userRole = userRoleRepository.findByUserUserIdAndRoleRoleId(user.getUserId(), roleType);
		if (userRole == null || !Boolean.TRUE.equals(userRole.isActive())) {
			throw new BadRequestException("User does not have required role");
		}

		// 5️ Generate Auth Token
		String token = generateJwtToken(user);

		VerifyOtpResponseDto response = new VerifyOtpResponseDto();
		response.setToken(token);
		response.setRoleName(roleType.getName());
		response.setUserId(user.getUserId());

		// 6️ Role-Specific Data Mapping
		switch (roleType) {

		case ORGANIZER:
			OrganizerDetails organizer = organizerRepository.findByOrgUserId(user.getUserId());

			if (organizer == null) {
				throw new BadRequestException("Organizer not found");
			}
			response.setOrganizerId(organizer.getOrgId());
			break;

		case SALESPERSON:
			UserRole salesPerson = userRoleRepository.findByUserUserIdAndRoleRoleId(user.getUserId(),
					RoleType.SALESPERSON);

			if (salesPerson == null) {
				throw new BadRequestException("Salesperson details not found");
			}

			response.setEventId(salesPerson.getEventId());
			response.setOrganizerId(salesPerson.getOrgId());
			break;
		case DOORMANAGER:

			UserRole doorManager = userRoleRepository.findByUserUserIdAndRoleRoleId(user.getUserId(),
					RoleType.DOORMANAGER);

			if (doorManager == null) {
				throw new BadRequestException("Salesperson details not found");
			}

			response.setEventId(doorManager.getEventId());
			response.setOrganizerId(doorManager.getOrgId());
			break;

			case CUSTOMER:
				response.setPhoneNumber(user.getPhoneNo());
				response.setUserId(user.getUserId());
		default:
			break;
		}

		// 7️ Cleanup OTP
		user.setOtpSend(null);
		user.setUpdatedOn(LocalDateTime.now());
		userRepository.save(user);
		OtpCache.removeOtp(dto.getPhoneNumber());

		return response;
	}

	private String generateJwtToken(User user) {
		long expirationMs = 24 * 60 * 60 * 1000;
		return Jwts.builder().setSubject(String.valueOf(user.getId())).claim("phoneNo", user.getPhoneNo())
				.claim("userName", user.getUserName()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationMs))
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
	}

	public void assignRoleToUser(User user, String roleName, EventDetails event, LocalDateTime roleStartDateTime,
			LocalDateTime roleEndDateTime, OrganizerDetails organizer) {

		if (roleName == null || roleName.isEmpty()) {
			throw new BadRequestException("Role is required");
		}

		RoleType roleType = roleService.getRoleType(roleName);
		Role role = roleService.getRoleById(roleType);

		boolean alreadyAssigned = userRoleRepository.existsByUserUserIdAndEventIdAndRoleRoleId(user.getUserId(),
				event.getEventId(), role.getRoleId());

		if (alreadyAssigned) {
			return;
		}

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		userRole.setOrgId(organizer.getOrgId());
		userRole.setEventId(event.getEventId());
		userRole.setRoleStartDateTime(roleStartDateTime != null ? roleStartDateTime : event.getEventStartDateTime());
		userRole.setRoleEndDateTime(roleEndDateTime != null ? roleEndDateTime : event.getEventEndDateTime());
		userRole.setCreatedBy(organizer.getOrgUserId());
		userRole.setCreatedOn(LocalDateTime.now());
		userRole.setActive(true);
		userRoleRepository.save(userRole);
	}

	public List<SalesPersonListDto> getAllSalesPersons(String orgId) {
		List<UserRole>  userRoles = userRoleRepository.findByOrgIdAndRoleRoleId(orgId, RoleType.SALESPERSON);
		List<SalesPersonListDto> dtos = new ArrayList<SalesPersonListDto>();
		for (UserRole userRole : userRoles) {
			SalesPersonListDto dto = new SalesPersonListDto();
			dto.setSalesPersonId(userRole.getUser().getUserId());
			dto.setSalesPersonId(userRole.getUser().getUserName());
			dto.setSalesPersonPhone(userRole.getUser().getPhoneNo());
			dto.setSalesPersonEmail(userRole.getUser().getEmailId());
			dto.setEventId(userRole.getEventId());
			dto.setOrgId(userRole.getOrgId());
			dtos.add(dto);
		}
		return dtos;
	}
}
