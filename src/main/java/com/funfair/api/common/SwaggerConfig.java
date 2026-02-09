package com.funfair.api.common;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
	info = @Info(
		title = "FairFun API",
		version = "1.0",
		description = "FunFair Application APIs Details"
	),
	security = @SecurityRequirement(name = "bearerAuth")
)
//@SecurityScheme(
//	name = "bearerAuth",
//	type = SecuritySchemeType.HTTP,
//	scheme = "bearer",
//	bearerFormat = "JWT"
//)
public class SwaggerConfig {
}
