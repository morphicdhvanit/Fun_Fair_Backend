package com.funfair.api.account.userrole;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.funfair.api.account.role.Role;
import com.funfair.api.account.role.RoleType;
import com.funfair.api.account.user.User;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

	List<UserRole> findByUserUserId(String userId);
	
	boolean existsByUserUserIdAndEventIdAndRoleRoleId(String userId, String eventId, RoleType roleId);
	
	UserRole findByEventIdAndRoleRoleIdAndOrgIdAndUserUserId(String eventId, RoleType doormanager, String orgId,String userId);
	
	UserRole findByUserUserIdAndRoleRoleId(String userId, RoleType role);

	List<UserRole> findByEventIdAndRoleRoleId(String eventId, RoleType salesperson);

	List<UserRole> findByEventIdAndRoleRoleIdAndOrgId(String eventId, RoleType doormanager, String orgId);

	List<UserRole> findByOrgIdAndRoleRoleId(String orgId, RoleType salesperson);

	boolean existsByUserAndRole(User user, Role role);
}
