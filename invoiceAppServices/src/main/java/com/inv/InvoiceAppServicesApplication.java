package com.inv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.inv.controller.AuthController;
import com.inv.model.Profile;

@SpringBootApplication
public class InvoiceAppServicesApplication extends SpringBootServletInitializer{
	
	@Autowired
	AuthController authController;
	
	@jakarta.annotation.PostConstruct
    public void init() {
		// Initial Check at the time of Deployment
		
		Profile profile = new Profile();
		profile.email = "test@test.com";
		profile.password = "test";
		profile.name = "test";		
		
		authController.save(profile);
		    
	}

	
	
	public static void main(String[] args) {
		SpringApplication.run(InvoiceAppServicesApplication.class, args);
		
		
	}
	
	}
