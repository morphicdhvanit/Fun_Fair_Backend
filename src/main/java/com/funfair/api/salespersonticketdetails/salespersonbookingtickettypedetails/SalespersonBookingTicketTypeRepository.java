package com.funfair.api.salespersonticketdetails.salespersonbookingtickettypedetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalespersonBookingTicketTypeRepository extends JpaRepository<SalespersonBookingTicketTypeDetails, Integer>{

}
