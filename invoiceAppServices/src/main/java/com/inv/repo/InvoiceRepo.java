package com.inv.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.inv.model.Invoice;


public interface InvoiceRepo extends PagingAndSortingRepository<com.inv.model.Invoice, Long> {

	

	public static final String SEARCH_QUERY = "SELECT * FROM INVOICE WHERE LOWER(CONCAT(INVOICE_NUMBER, ' ', INVOICE_DATE, ' ', SOLD_TO, ' ', SHIP_TO,' ', SALESMAN, ' ', ORDER_NUMBER, ' ', CUST_ORDER_NUMBER, ' ')) LIKE LOWER(concat('%', ?1, '%'))";
	@Query(value = SEARCH_QUERY, nativeQuery = true)
	public ArrayList<Invoice> searchInvoice(String searchField);
	
	void deleteByInvoiceNumber(String invoiceNumber);

	Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

	Invoice save(Invoice invoice);

	public Optional<Invoice> findById(long id);

	public void deleteById(long id);
	 
	List<Invoice> findByInvoiceDateBetween(Date start, Date end);

	public Long count();

	//public Invoice findTopByInvoiceDate();


	
	
//	
//	public static final String FIND_NAME_BY_TASKID = "SELECT COUNT(*) FROM PRODUCT_INVENTORY where MASTER_ID=:masterId AND CHECKED_IN=true";
// 	@Query(value = FIND_NAME_BY_TASKID, nativeQuery = true)
//	long findInventoryCount(String masterId);
//	
// 	Optional<ProductInventory> findBySkuId(String skuId);
//
//	Page<ProductInventory> findByCheckedInTrue(Pageable paging);
//		
//	ArrayList<ProductInventory> findByCheckedInTrue();
//
//	Page<ProductInventory> findByMasterIdAndCheckedInTrue(String masterId, Pageable paging);
//	
//	public static final String SEARCH_QUERY = "SELECT * FROM PRODUCT_INVENTORY WHERE LOWER(CONCAT(SKU_ID, ' ', ENTRY_TYPE, ' ', LOCATION_CODE, ' ', CHECK_IN_DATE,' ','CHECK_IN_TYPE', ' ' )) LIKE LOWER(concat('%', ?1, '%')) AND CHECKED_IN=true";
// 	@Query(value = SEARCH_QUERY, nativeQuery = true)
// 	public ArrayList<ProductInventory> searchAvailableInventory(String searchField);
//
// 	
// 	
// 	public static final String GET_SKUIDS_BY_LOC_CODE = "SELECT DISTINCT SKU_ID FROM PRODUCT_INVENTORY where LOCATION_CODE=:locationCode AND CHECKED_IN=true";
// 	@Query(value = GET_SKUIDS_BY_LOC_CODE, nativeQuery = true)
//	HashSet<String> getSkuIdsByLocationCode(String locationCode);
//
// 	public long countByCheckedInTrue();
// 	
// 	public static final String GET_REENTRY_COUNT = "SELECT COUNT(*) FROM PRODUCT_INVENTORY where ENTRY_COUNT > 0 AND CHECKED_IN=true";
// 	@Query(value = GET_REENTRY_COUNT, nativeQuery = true)
//	public long countReEnteredItemsCount();
// 	
 	
}



