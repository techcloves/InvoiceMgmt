package com.inv.service;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inv.model.Profile;
import com.inv.repo.AuthRepo;

import java.util.Optional;

@Service
public class AuthService {

    @SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private AuthRepo repo;

    public Profile save(Profile object) {
         return repo.save(object);
    }

	public List<Profile> findAll() {
		return repo.findAll();
	}

	public Boolean existingUser(String email) {
		Optional<Profile> option = repo.findByEmail(email);
		if(option.isPresent())
		{
			Profile object1 = option.get();
			return true;
		}
			else
			return false;
	}
	
	public Optional<Profile> findByEmail(String email) {
		return repo.findByEmail(email);
	}
}
