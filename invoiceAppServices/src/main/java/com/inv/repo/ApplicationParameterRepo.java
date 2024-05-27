package com.inv.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.inv.model.ApplicationParameter;

@Repository
public interface ApplicationParameterRepo extends JpaRepository<com.inv.model.ApplicationParameter,Long> {


	
}
