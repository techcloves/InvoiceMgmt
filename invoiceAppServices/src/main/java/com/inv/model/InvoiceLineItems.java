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

	public class InvoiceLineItems {

		
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	  	public Long id;
		
	//	public Long invoiceId;
	   
		public String styleNumber;
		public String description;
		public Long quantityOrdered;
		public Long quantityShipped;
		public Float unitPrice;
		public Float amount;
		  
	 }



