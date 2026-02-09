package com.funfair.api.account.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceDetails, Integer>{

	DeviceDetails findByUserId(String userId);

}
