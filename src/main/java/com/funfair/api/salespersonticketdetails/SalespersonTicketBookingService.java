package com.funfair.api.salespersonticketdetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.account.role.Role;
import com.funfair.api.account.role.RoleRepository;
import com.funfair.api.account.user.User;
import com.funfair.api.account.user.UserRepository;
import com.funfair.api.account.userrole.UserRole;
import com.funfair.api.account.userrole.UserRoleRepository;
import com.funfair.api.common.QrCodeService;
import com.funfair.api.common.Util;
import com.funfair.api.exception.BadRequestException;
import com.funfair.api.salespersonticketdetails.salespersonbookingtickettypedetails.AddSalesPersonBookingTicketTypeDto;
import com.funfair.api.salespersonticketdetails.salespersonbookingtickettypedetails.SalespersonBookingTicketTypeDetails;
import com.funfair.api.salespersonticketdetails.salespersonbookingtickettypedetails.SalespersonBookingTicketTypeRepository;

@Service
public class SalespersonTicketBookingService {

	@Autowired
	SalespersonTicketBookingRepository salespersonTicketBookingRepository;
	@Autowired
	SalespersonBookingTicketTypeRepository salespersonBookingTicketTypeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	QrCodeService qrCodeService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	Util util;
	@Autowired
	UserRoleRepository userRoleRepository;

	public SalespersonTicketBookingDetails bookTicket(BookTicketDetailsDto bookTicketDetailsDto) {
		if (bookTicketDetailsDto == null) {
			throw new BadRequestException("BookTicketDetailsDto cannot be null");
		}
		SalespersonTicketBookingDetails bookingDetails = convertBookingDtailsDtotoEntity(bookTicketDetailsDto);
		SalespersonTicketBookingDetails savedBookingDetails = salespersonTicketBookingRepository.save(bookingDetails);

		// Step 2: Save ticket type details
		saveTicketTypeDetails(bookTicketDetailsDto, bookingDetails.getTicketNumber());

		User user = createOrGetCustomer(bookTicketDetailsDto);

		assignCustomerRole(user, bookTicketDetailsDto);

		return savedBookingDetails;
	}

	private SalespersonTicketBookingDetails convertBookingDtailsDtotoEntity(BookTicketDetailsDto bookTicketDetailsDto) {
		SalespersonTicketBookingDetails bookingDetails = new SalespersonTicketBookingDetails();
		bookingDetails.setEventId(bookTicketDetailsDto.getEventId());
		bookingDetails.setSalespersonId(bookTicketDetailsDto.getSalespersonId());
		bookingDetails.setEventId(bookTicketDetailsDto.getEventId());
		bookingDetails.setOrganizerId(bookTicketDetailsDto.getOrganizerId());
		bookingDetails.setBuyerName(bookTicketDetailsDto.getBuyerName());
		bookingDetails.setBuyerPhoneNumber(bookTicketDetailsDto.getBuyernumber());
		bookingDetails.setPaymentMethod(getPaymentMethod(bookTicketDetailsDto.getPaymentMethod()));
		bookingDetails.setTotalPaymentAmount(bookTicketDetailsDto.getTotalPaymentAmount());
		bookingDetails.setTicketNumber(generateTicketNumber());
		bookingDetails.setCreatedBy(bookTicketDetailsDto.getCreatedBy());
		bookingDetails.setCreatedOn(LocalDateTime.now());
		bookingDetails.setTotalquantity(bookTicketDetailsDto.getTotalquantity());
		bookingDetails.setTicketQrToken(generateTicketToken());
		return bookingDetails;
	}

	private PaymnetMethodTypeEnum getPaymentMethod(String paymentMethod) {
		PaymnetMethodTypeEnum type = null;
		if (paymentMethod.equalsIgnoreCase(PaymnetMethodTypeEnum.CARD.getName()))
			type = PaymnetMethodTypeEnum.CARD;
		if (paymentMethod.equalsIgnoreCase(PaymnetMethodTypeEnum.CASH.getName()))
			type = PaymnetMethodTypeEnum.CASH;
		if (paymentMethod.equalsIgnoreCase(PaymnetMethodTypeEnum.UPIPAYMNET.getName()))
			type = PaymnetMethodTypeEnum.UPIPAYMNET;
		if (type == null)
			throw new BadRequestException("Invalid Paymnet Method type: " + type);
		return type;
	}

	private String generateTicketToken() {

		return UUID.randomUUID().toString();

	}

	private String generateTicketNumber() {

		String prefix = "FF";

		String year = String.valueOf(LocalDate.now().getYear());

		Long count = salespersonTicketBookingRepository.count() + 1;

		return prefix + "-" + year + "-" + String.format("%06d", count);

	}

	private User createOrGetCustomer(BookTicketDetailsDto dto) {

		User optionalUser = userRepository.findByPhoneNo(dto.getBuyernumber());

		if (optionalUser != null) {
			return optionalUser;
		}

		// Create new user
		User user = new User();

		user.setUserName(dto.getBuyerName());
		user.setPhoneNo(dto.getBuyernumber());
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

	private void saveTicketTypeDetails(BookTicketDetailsDto dto, String ticketNumber) {

		double finalTotal = 0;

		for (AddSalesPersonBookingTicketTypeDto typeDto : dto.getTicketTypeDetailsList()) {

			SalespersonBookingTicketTypeDetails entity = new SalespersonBookingTicketTypeDetails();

			entity.setSalespersonId(dto.getSalespersonId());
			entity.setEventId(dto.getEventId());
			entity.setOrganizerId(dto.getOrganizerId());

			entity.setTicketNumber(ticketNumber);

			entity.setTicketTypeId(typeDto.getTicketTypeId());

			int quantity = typeDto.getTicketQuantity();

			double price = typeDto.getOneTicketPrice();

			double total = quantity * price;

			entity.setTicketQuantity(quantity);

			entity.setOneTicketPrice(price);

			entity.setTotalTicketPrice(total);

			entity.setCreatedBy(dto.getCreatedBy());

			entity.setCreatedOn(LocalDateTime.now());

			salespersonBookingTicketTypeRepository.save(entity);

			finalTotal += total;
		}

		System.out.println("Final Total = " + finalTotal);

	}
	

public byte[] getTicketQrCode(String ticketNumber) {

    SalespersonTicketBookingDetails ticket =
            salespersonTicketBookingRepository.findByTicketNumber(ticketNumber);

    if (ticket == null) {

        throw new BadRequestException("Ticket not found");

    }

    return qrCodeService.generateQrCode(
            ticket.getTicketQrToken());

}

}
