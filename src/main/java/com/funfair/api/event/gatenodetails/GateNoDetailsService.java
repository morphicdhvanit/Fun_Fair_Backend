package com.funfair.api.event.gatenodetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.common.Util;
import com.funfair.api.exception.BadRequestException;

@Service
public class GateNoDetailsService {

	@Autowired
	GateNoDetailsRepository gateNoDetailsRepository;
	@Autowired
	Util util;

	public List<GateNoDetails> saveGateNoDetails(AddGateNoDetailsDto dto) {

		if (dto == null || dto.getGateNoDetails() == null || dto.getGateNoDetails().isEmpty()) {
			throw new BadRequestException("Invalid gate details request.");
		}

		List<GateNoDetails> savedDetails = new ArrayList<>();

		for (AddGateNoDto gateDto : dto.getGateNoDetails()) {

			// 🔴 Duplicate check
			boolean exists = gateNoDetailsRepository.existsByEventIdAndUserIdAndGateNoDate(dto.getEventId(),
					dto.getUserId(), gateDto.getGateNumberDate());

			if (exists) {
				throw new BadRequestException("Duplicate entry found for gate no " + gateDto.getGateNo() + " at "
						+ gateDto.getGateNumberDate());
			}

			// ✅ Create new entry
			GateNoDetails details = new GateNoDetails();
			details.setEventId(dto.getEventId());
			details.setGateNo(gateDto.getGateNo());
			details.setUserId(dto.getUserId());
			details.setOrgId(dto.getOrgId());
			details.setGateNoDate(gateDto.getGateNumberDate());
			details.setDoorManageGateNumberId(util.generateRandomNumericId());
			details.setCreatedBy(dto.getCreatedBy());
			details.setCreatedOn(LocalDateTime.now());
			details.setActive(true);

			savedDetails.add(details);
		}

		// ✅ Save all at once
		return gateNoDetailsRepository.saveAll(savedDetails);
	}

}
