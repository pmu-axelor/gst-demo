package com.axelor.gst.services;

import java.time.LocalDateTime;
import java.util.List;

import com.axelor.gst.db.Company;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.repo.CompanyRepository;
import com.axelor.gst.db.repo.InvoiceRepository;
import com.axelor.gst.db.repo.PartyRepository;
import com.axelor.gst.db.repo.ProductRepository;
import com.axelor.inject.Beans;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class ProductServiceImpl implements ProductService {
	  
	    protected ProductRepository productRepository;
	    protected CompanyRepository companyRepository;
	    protected PartyRepository partyRepository; 
	    protected InvoiceRepository invoiceRepository;
	   
	    @Inject
	    public ProductServiceImpl(ProductRepository productRepository,CompanyRepository companyRepository,
	    		PartyRepository partyRepository,InvoiceRepository invoiceRepository) {
	    	
	    	 this.productRepository = productRepository;
	    	 this.companyRepository = companyRepository;
	    	 this.partyRepository = partyRepository;
	    	 this.invoiceRepository = invoiceRepository;
	    	 
	     }  

	     @Override
	     @Transactional
	     public Invoice createInvoice(List<Integer> ids) {
		
				/*
				 * ProductRepository pRepo = Beans.get(ProductRepository.class);
				 * CompanyRepository cmpRepo = Beans.get(CompanyRepository.class);
				 * PartyRepository prtyRepo = Beans.get(PartyRepository.class);
				 */
	    
	    
	    // InvoiceRepository invRepo = Beans.get(InvoiceRepository.class);
	      
	    	 Company company = companyRepository.all().fetchOne();
		     Party party = partyRepository.all().fetchOne();
	         Invoice invoice = new Invoice();    
	         Product product = new Product();
			/*
			 * invoice.setInvoiceAddress(party.getAddress().get(0));
			 * invoice.setShippingAddress(party.getAddress().get(0));
			 */
	      
	      invoice.setStatus("draft"); 
	      invoice.setDates(LocalDateTime.now());
	      invoice.setCompany(company);
	      invoice.setParty(party);
	      //invoice.setPartyContact(party.getContact().get(0));
	      AddressServiceImpl addressService = Beans.get(AddressServiceImpl.class);
	      addressService.getInvoiceAddresses(invoice);
	      addressService.getShippingAddresses(invoice);
	      InvoiceLineService invoiceLineService = Beans.get(InvoiceLineService.class);
	      InvoiceService invoiceService = Beans.get(InvoiceService.class);
	      System.out.println(invoiceService);
	
	     for(Integer l : ids){
	    	      product = productRepository.find(Long.valueOf(l));
	    	      System.out.println(product);
	    	      InvoiceLine invcLine = new InvoiceLine();
	    	      invcLine.setProduct(product);
	    	      invcLine.setHsbn(product.getHsbn());
	    	      invcLine.setItem(product.getCode()+"-"+product.getName());
	    	      invcLine.setGstRate(product.getGstRate());
	    	      invcLine.setPrice(product.getSalePrice());
	    	      invcLine.setQty(1);
	    	      invoiceLineService.computeInvoiceLinesItems(invoice, invcLine);
	    	   //   invoiceService.computeInvoices(invoice);
	    	      invoice.addInvoiceItem(invcLine);
	    	      invoiceService.computeInvoices(invoice);
	    	      
	    	    }
	     
	         invoiceRepository.save(invoice);
	         
	         
	         return invoice;	
	    
	     }

             

}
