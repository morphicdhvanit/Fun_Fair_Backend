package com.funfair.api.salespersonticketbookingdetails;

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
import com.funfair.api.bookingticketdetails.BookTicketDetailsDto;
import com.funfair.api.bookingticketdetails.BookingTicketService;
import com.funfair.api.bookingticketdetails.BookingTicketDetails;
import com.funfair.api.bookingticketdetails.bookingtickettypedetails.BookingTicketTypeDetails;
import com.funfair.api.bookingticketdetails.bookingtickettypedetails.BookingTicketTypeDetailsDto;
import com.funfair.api.bookingticketdetails.bookingtickettypedetails.BookingTicketTypeRepository;
import com.funfair.api.common.QrCodeService;
import com.funfair.api.common.Util;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.ticketavailability.tickettypeacailability.TicketTypeAvilabilityDetails;
import com.funfair.api.ticketavailability.tickettypeacailability.TickettypeAvilabilityRepository;
import com.funfair.api.tickettypedetails.TicketTypeDetails;
import com.funfair.api.tickettypedetails.TicketTypeDetailsRepository;

@Service
public class SalespersonTicketBookingService {

	@Autowired
	SalespersonTicketBookingRepository salespersonTicketBookingRepository;
	@Autowired
	TicketTypeDetailsRepository ticketTypeDetailsRepository;
	@Autowired
	BookingTicketService bookingTicketService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	Util util;
	@Autowired
	UserRoleRepository userRoleRepository;

	public SalespersonTicketBookingDetails bookTicket(BookTicketDetailsDto dto) {

		if (dto == null) {
			throw new BadRequestException("BookTicketDetailsDto cannot be null");
		}
		BookTicketDetailsDto bookingDto = convertToBookingDto(dto);

		BookingTicketDetails bookingTicket = bookingTicketService.createBookingTicket(bookingDto);

		SalespersonTicketBookingDetails salespersonBooking = convertToSalespersonEntity(dto, bookingTicket);

		SalespersonTicketBookingDetails savedBooking = salespersonTicketBookingRepository.save(salespersonBooking);

		User user = createOrGetCustomer(dto);

		assignCustomerRole(user, dto);

		return savedBooking;

	}

	private SalespersonTicketBookingDetails convertToSalespersonEntity(BookTicketDetailsDto dto,
			BookingTicketDetails bookingTicket) {

		SalespersonTicketBookingDetails entity = new SalespersonTicketBookingDetails();

		entity.setSalespersonId(dto.getSalespersonId());

		entity.setEventId(dto.getEventId());

		entity.setOrganizerId(dto.getOrganizerId());

		entity.setTotalPaymentAmount(bookingTicket.getTotalPaymentAmount());

		entity.setTicketNumber(bookingTicket.getTicketNumber());

		entity.setCreatedBy(dto.getCreatedBy());

		entity.setCreatedOn(LocalDateTime.now());

		return entity;

	}

	private BookTicketDetailsDto convertToBookingDto(BookTicketDetailsDto dto) {

		BookTicketDetailsDto bookingDto = new BookTicketDetailsDto();

		bookingDto.setEventId(dto.getEventId());

		bookingDto.setOrganizerId(dto.getOrganizerId());

		bookingDto.setSalespersonId(dto.getSalespersonId());

		bookingDto.setBuyerName(dto.getBuyerName());

		bookingDto.setTotalquantity(dto.getTotalquantity());

		bookingDto.setBuyerPhoneNumber(dto.getBuyerPhoneNumber());

		bookingDto.setPaymentMethod(dto.getPaymentMethod());

		bookingDto.setTicketAssignBySalesPerson(true);

		bookingDto.setCreatedBy(dto.getCreatedBy());

		List<BookingTicketTypeDetailsDto> typeList = new ArrayList<>();

		for (BookingTicketTypeDetailsDto typeDto : dto.getBookTicketDetailsDtos()) {

			BookingTicketTypeDetailsDto t = new BookingTicketTypeDetailsDto();

			t.setTicketTypeId(typeDto.getTicketTypeId());

			t.setTicketQuantity(typeDto.getTicketQuantity());

			t.setOneTicketPrice(typeDto.getOneTicketPrice());

			typeList.add(t);
		}

		bookingDto.setBookTicketDetailsDtos(typeList);

		return bookingDto;

	}

	private User createOrGetCustomer(BookTicketDetailsDto dto) {

		User optionalUser = userRepository.findByPhoneNo(dto.getBuyerPhoneNumber());

		if (optionalUser != null) {
			return optionalUser;
		}

		// Create new user
		User user = new User();

		user.setUserName(dto.getBuyerName());
		user.setPhoneNo(dto.getBuyerPhoneNumber());
		user.setUserId(util.generateRandomNumericId());
		user.setCreatedOn(LocalDateTime.now());

		return userRepository.save(user);

	}

	private void assignCustomerRole(User user, BookTicketDetailsDto dto) {

		Role role = roleRepository.findByRoleName("customer");

		if (role == null) {
			throw new BadRequestException("Customer role not found");

		}

		// Check if role already exists
		boolean exists = userRoleRepository.existsByUserAndRole(user, role);
		if (exists) {
			return;
		}
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		userRole.setCreatedBy(user.getUserName());
		userRole.setCreatedOn(LocalDateTime.now());
		userRoleRepository.save(userRole);

	}

	public List<SalesPersonSoldTicketDetailsDto> byEventIdAndSalesPersonId(String eventId, String salespersonId) {

		List<SalespersonTicketBookingDetails> entityList = salespersonTicketBookingRepository
				.findByEventIdAndSalespersonIdAndIsActiveTrue(eventId, salespersonId);

		List<SalesPersonSoldTicketDetailsDto> dtoList = new ArrayList<>();

		for (SalespersonTicketBookingDetails entity : entityList) {

			dtoList.add(convertEntityToDto(entity));

		}

		return dtoList;

	}

	private SalesPersonSoldTicketDetailsDto convertEntityToDto(SalespersonTicketBookingDetails entity) {

		SalesPersonSoldTicketDetailsDto dto = new SalesPersonSoldTicketDetailsDto();

		dto.setEventId(entity.getEventId());

		dto.setTicketpurchaseDateTime(entity.getCreatedOn());

		BookingTicketDetails bookingTicketDetails = bookingTicketService.getByTicketNumber(entity.getTicketNumber());
		System.out.println("bookingTicketDetails  "+ bookingTicketDetails);
		User user = userRepository.findByUserId(bookingTicketDetails.getCustomerId());
		System.out.println("user "+ user);
		dto.setBuyerName(user.getUserName());

		dto.setBuyerPhoneNumber(user.getPhoneNo());

		// Ticket Type Details
		List<BookingTicketTypeDetails> ticketTypes = bookingTicketService
				.GetTicketTypeDetailsByTicketNumber(entity.getTicketNumber());

		List<SalesPersonSoldTicketTypeDetailsDto> typeDtoList = new ArrayList<>();

		for (BookingTicketTypeDetails type : ticketTypes) {

			SalesPersonSoldTicketTypeDetailsDto typeDto = new SalesPersonSoldTicketTypeDetailsDto();

			typeDto.setTicketTypeId(type.getTicketTypeId());
			TicketTypeDetails ticketTypeDetails  = ticketTypeDetailsRepository.findByTicketTypeId(type.getTicketTypeId());

			typeDto.setTicketTypeName(ticketTypeDetails.getTicketName());

			typeDto.setTicketQuantity(type.getTicketQuantity());

			typeDtoList.add(typeDto);

		}

		dto.setSalesPersonSoldTicketTypeDetailsDtos(typeDtoList);

		return dto;

	}

//	public List<SalesPersonSoldTicketDetailsDto> byEventIdAndSalesPersonId(String eventId, String salespersonId) {
//		
//		return null;
//	}

}
