package com.inv.model;
	
	import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.CascadeType;
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

	public class Invoice {

		
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	  	public Long id;
	   
		
		
		public String invoiceNumber;
		public Date invoiceDate;
		public String soldTo;
		public String shipTo;
		public String orderNumber;
		public String custOrderNumber;
		public String deptNumber;
		public String shipVia;
		public String terms;
		public String salesman;
		public Long boxes;
		public Boolean taxRequired;
		public Float taxPercent;
		public String taxDescription;
		public Float frieghtCharges;
		public String frieightDescription;
		public Float totalPreTax;
		public Float totalAfterTax;
		public String specialInstructions;

	//	public ArrayList<InvoiceLineItems> invoiceLineItems;
		
		@OneToMany(cascade = CascadeType.ALL)
	    @JoinColumn(name="invoiceId", referencedColumnName="id")
	    public InvoiceLineItems[] invoiceLineItems;
		
	    
	    @Transient
	    public APIResponse apiResponse = new APIResponse();
	      
	 }



