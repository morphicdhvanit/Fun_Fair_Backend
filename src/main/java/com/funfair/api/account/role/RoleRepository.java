package com.funfair.api.account.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByRoleName(String string);


	Role findByRoleId(RoleType roleId);

}
