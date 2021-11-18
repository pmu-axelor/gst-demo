package com.axelor.gst.Services;

import java.util.ArrayList;
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
	     public void createInvoice(List<Integer> ids) {
		
		 ProductRepository pRepo = Beans.get(ProductRepository.class);
		 CompanyRepository cmpRepo = Beans.get(CompanyRepository.class);
	     PartyRepository prtyRepo = Beans.get(PartyRepository.class);
	     Company company = cmpRepo.all().fetchOne();
	     Party party = prtyRepo.all().fetchOne();
	    
	     InvoiceRepository invRepo = Beans.get(InvoiceRepository.class);
	     
	     Invoice invoice = new Invoice();    
	     List<InvoiceLine> lines = new ArrayList<>();
	     Product product = new Product();
	
	     for(Integer l : ids){
	    	      product = pRepo.find(Long.valueOf(l));
	    	      System.out.println(product);
	    	      InvoiceLine invcLine = new InvoiceLine();
	    	      invcLine.setProduct(product);
	    	      invcLine.setHsbn(product.getHsbn());
	    	      invcLine.setItem(product.getCode()+"-"+product.getName());
	    	      invcLine.setGstRate(product.getGstRate());
	    	      invcLine.setPrice(product.getSalePrice());
	    	       lines.add(invcLine);
	    	    }
	     
	         invoice.setInvoiceItems(lines);
	         invoice.setCompany(company);
	         invoice.setParty(party);
	         
	         invRepo.save(invoice);
	    
	     }

	      

}
