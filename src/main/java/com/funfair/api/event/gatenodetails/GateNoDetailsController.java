package com.funfair.api.event.gatenodetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gate-no-details")
public class GateNoDetailsController {
	
	@Autowired
	GateNoDetailsService gateNoDetailsService;

}
