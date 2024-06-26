package com.inv.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inv.model.ApplicationParameter;
import com.inv.model.Invoice;
import com.inv.repo.ApplicationParameterRepo;



@CrossOrigin
@RestController
@RequestMapping("/appParameter")
public class ApplicationParametersController {

		
	@Autowired
	ApplicationParameterRepo repository;
	
	
	@GetMapping("/")
	public List<ApplicationParameter> getAllEntries() {

		List<ApplicationParameter> entries = new ArrayList<>();
		repository.findAll().forEach(entries::add);

		return entries;
	}
	

	
	@PutMapping("/update/{id}")
	public ResponseEntity<ApplicationParameter> updateResource(@PathVariable long id, @RequestBody ApplicationParameter entry) {
		
		Optional<ApplicationParameter> resourceData = repository.findById(id);

		if (resourceData.isPresent()) {
			ApplicationParameter _resource = resourceData.get();
			_resource.keyField = entry.keyField;
			_resource.keyValue = entry.keyValue;
	
			
			return new ResponseEntity<>(repository.save(_resource), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}
	
	@PutMapping("/updateAll")
	public ResponseEntity<ApplicationParameter> updateAll(@RequestBody List<ApplicationParameter> entries) {
		
	entries.forEach((entry) -> 
	{
		Optional<ApplicationParameter> resourceData = repository.findById(entry.id);
		if (resourceData.isPresent()) {
			ApplicationParameter _resource = resourceData.get();
			_resource.keyField = entry.keyField;
			_resource.keyValue = entry.keyValue;
			repository.save(_resource);
		}
	}); 
	return new ResponseEntity<>(HttpStatus.OK);
			
	
	}
		
		
		
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAllEntries() {
	
		repository.deleteAll();

		return new ResponseEntity<>("All entries have been deleted!", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEntry(@PathVariable long id) {
	
		repository.deleteById(id);

		return new ResponseEntity<>("Entry has been deleted!", HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
	public ApplicationParameter postEntry(@RequestBody ApplicationParameter resource) {
		ApplicationParameter _resource = repository.save(resource);
		return _resource;
	}
}
