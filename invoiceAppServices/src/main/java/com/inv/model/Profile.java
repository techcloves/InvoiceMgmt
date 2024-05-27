package com.inv.model;
	
	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.OneToOne;
	import jakarta.persistence.Transient;
	import jakarta.persistence.UniqueConstraint;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor

	public class Profile {

		
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	  	public Long id;
	   
		
		public String name;
		
	    @Column(unique=true)
	    public String email;
	    public String password;
	    
	    @Transient
	    public String changedNewPassword;
	    
	    @Transient
	    public APIResponse apiResponse = new APIResponse();
	      
	 }



