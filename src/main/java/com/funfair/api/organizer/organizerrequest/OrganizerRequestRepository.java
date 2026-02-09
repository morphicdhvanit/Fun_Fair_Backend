package com.funfair.api.organizer.organizerrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizerRequestRepository extends JpaRepository<OrganizerRequestDetails, Integer>{

	OrganizerRequestDetails findByOrganizerRequestId(String organizerRequestId);

}
