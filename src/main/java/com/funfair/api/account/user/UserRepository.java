package com.funfair.api.account.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository	
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByPhoneNo(String phoneNumber);

	User findByUserId(String userId);

}
