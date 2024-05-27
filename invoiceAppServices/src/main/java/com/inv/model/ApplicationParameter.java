package com.inv.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationParameter {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
  	public Long id;
	
    public String keyField;
    public String keyValue;
 	
}