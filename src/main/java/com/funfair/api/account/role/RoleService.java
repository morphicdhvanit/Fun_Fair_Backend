package com.funfair.api.account.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.event.EventTypeEnum;
import com.funfair.api.exception.BadRequestException;

@Service

public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
    public Role getRoleById  (RoleType roleId) {
		Role role = roleRepository.findByRoleId(roleId);
		return role;
    }
    
    
	public RoleType getRoleType(String eventType) {
		RoleType type = null;
		if (eventType.equalsIgnoreCase(RoleType.CUSTOMER.name()))
			type = RoleType.CUSTOMER;
		if (eventType.equalsIgnoreCase(RoleType.DOORMANAGER.name()))
			type = RoleType.DOORMANAGER;
		if (eventType.equalsIgnoreCase(RoleType.ORGANIZER.name()))
			type = RoleType.ORGANIZER;
		if (eventType.equalsIgnoreCase(RoleType.SALESPERSON.name()))
			type = RoleType.SALESPERSON;
		if (type == null)
			throw new BadRequestException("Invalid Role type: " + type);
		return type;
	}
   

}
