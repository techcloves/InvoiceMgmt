package com.inv.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inv.model.Invoice;
import com.inv.repo.InvoiceRepo;

@CrossOrigin
@RestController
@RequestMapping("/invoice")
public class InvoiceController {
	
	@Autowired
	InvoiceRepo repository;
	
	@GetMapping("/getInvoicesCount")
	public Long getInvoicesCount() {
  
    return repository.count();
		  
		  }
	
	@GetMapping("/getPagedInvoices")
	public List<Invoice> getPagedInvoices(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "60") int size) {
  
		    Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"id"));
	        Page<Invoice> pagedResult = repository.findAll(paging);
	        
	
	        if(pagedResult.hasContent()) {
	        	
	        	List<Invoice> entries = pagedResult.getContent();
	        		        	
	        	return pagedResult.getContent();
	        } else {
	            return null;
	        }
		  }

	@GetMapping(value = "/getByInvoiceNumber/{invoiceNumber}")
	public ResponseEntity<Invoice> findById(@PathVariable String invoiceNumber) {

		Optional<Invoice> data = repository.findByInvoiceNumber(invoiceNumber);
		if (data.isPresent())
		{
			Invoice invoice = data.get();
			
			return new ResponseEntity<Invoice>(invoice, HttpStatus.OK); 
		}
		
		else {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
			  } 
		}

//	
//	@GetMapping("/lastInvoiceNumber/{sequence}")
//	public String lastInvoiceNumber(@PathVariable String sequence) {
//
//	Invoice invoice = repository.findTopByInvoiceDate();
//					
//	return invoice.invoiceNumber;
//	}
	
	
	@GetMapping(value = "/getById/{id}")
	public ResponseEntity<Invoice> findById(@PathVariable Long id) {

		Optional<Invoice> data = repository.findById(id);
		if (data.isPresent())
		{
			Invoice invoice = data.get();
			
			return new ResponseEntity<Invoice>(invoice, HttpStatus.OK); 
		}
		
		else {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
			  } 
		}

		
	@GetMapping("/search/{searchField}")
	public ArrayList<Invoice> searchString(@PathVariable String searchField) {
		
		ArrayList<Invoice> entries = new ArrayList<>();
		repository.searchInvoice(searchField).forEach(entries::add);
		return entries;
	}
	
	 @GetMapping("/searchBetweenDates")
	    public List<Invoice> searchBetweenDates(
	        @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
	        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
	        
	        return repository.findByInvoiceDateBetween(start, end);
	    }
	
	@PostMapping(value = "/create")
	public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {

				repository.save(invoice);
				
				return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
			}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEntry(@PathVariable long id) {
	
		repository.deleteById(id);

		return new ResponseEntity<>("Entry has been deleted!", HttpStatus.OK);
	}

	
	@PutMapping("/update/{id}")
	public ResponseEntity<Invoice> updateEntry(@PathVariable long id, @RequestBody Invoice invoice) {
	
		Optional<Invoice> invoiceDB = repository.findById(id);

		if (invoiceDB.isPresent()) {
		Invoice _invoice = invoiceDB.get();
		
		
		
		return new ResponseEntity<Invoice>(repository.save(invoice), HttpStatus.OK);
		} 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}

	

}
