package com.funfair.api.event.gatenodetails;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GateNoDetailsRepository extends JpaRepository<GateNoDetails, Integer> {

	List<GateNoDetails> findByEventId(String eventId);

	boolean existsByEventIdAndUserIdAndGateNoDate(String eventId, String userId, LocalDateTime gateNumberDate);

	List<GateNoDetails> findByEventIdAndOrgId(String eventId, String orgId);

}
