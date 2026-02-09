package com.funfair.api.account.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/role")
@CrossOrigin("*")
public class RoleController {
	@Autowired
	RoleService roleService;

}
