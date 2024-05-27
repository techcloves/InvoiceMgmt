package com.inv.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.inv.model.FileModel;


@Repository
public interface FileRepo extends JpaRepository<com.inv.model.FileModel,Long> {

	Optional<FileModel> findByProfileId(long id);
    
}

