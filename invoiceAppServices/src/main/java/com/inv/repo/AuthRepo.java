package com.inv.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.inv.model.Profile;


@Repository
public interface AuthRepo extends JpaRepository<com.inv.model.Profile,Long> {

	Optional<Profile> findByEmail(String email);
    

}

