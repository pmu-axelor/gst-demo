package com.axelor.gst.Services;

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
import com.google.inject.persist.Transactional;

public class ProductServiceImpl implements ProductService {

	     @Override
	     @Transactional
	     public void createInvoice(List<Integer> ids,Invoice invoice) {
		
		 ProductRepository pRepo = Beans.get(ProductRepository.class);
		 CompanyRepository cmpRepo = Beans.get(CompanyRepository.class);
	     PartyRepository prtyRepo = Beans.get(PartyRepository.class);
	     Company company = cmpRepo.all().fetchOne();
	     Party party = prtyRepo.all().fetchOne();
	    
	     InvoiceRepository invRepo = Beans.get(InvoiceRepository.class);
	      
		
	     // Invoice invoice = new Invoice();    
	      Product product = new Product();
	      invoice.setInvoiceAddress(party.getAddress().get(0));
	      invoice.setShippingAddress(party.getAddress().get(0));
	      
	      invoice.setStatus("draft"); 
	      invoice.setDates(LocalDateTime.now());
	      invoice.setCompany(company);
	      invoice.setParty(party);
	      invoice.setPartyContact(party.getContact().get(0));
	
	     for(Integer l : ids){
	    	      product = pRepo.find(Long.valueOf(l));
	    	      System.out.println(product);
	    	      InvoiceLine invcLine = new InvoiceLine();
	    	      invcLine.setProduct(product);
	    	      invcLine.setHsbn(product.getHsbn());
	    	      invcLine.setItem(product.getCode()+"-"+product.getName());
	    	      invcLine.setGstRate(product.getGstRate());
	    	      invcLine.setPrice(product.getSalePrice());
	    	      invoice.addInvoiceItem(invcLine);
	    	    }
	     
	        // invoice.setInvoiceItems(lines);
	         invoice.setCompany(company);
	         invoice.setParty(party);
	         
	         invRepo.save(invoice);
	    
	     }

	      

}
