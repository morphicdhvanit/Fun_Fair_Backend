package com.funfair.api.organizer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.funfair.api.organizer.organizerrequest.OrganizerRequestDetails;

@Repository

public interface OrganizerRepository extends JpaRepository<OrganizerDetails, Integer> {

	OrganizerDetails findByOrgId(String orgId);

	OrganizerDetails findByOrgUserId(String userId);


}
