package com.axelor.gst.Controller;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.axelor.gst.Services.ProductService;
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
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.meta.schema.actions.ActionView.ActionViewBuilder;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;
import com.google.inject.persist.Transactional;

public class ProductController {
	  
	   @Transactional
	   public void getInvoiceForm(ActionRequest request, ActionResponse response) {
		
		    //  System.out.println(request.getContext().entrySet());
		   
		    List<Integer> ids = (List<Integer>) request.getContext().get("_ids");
		    System.out.println(ids);
		    
		           Beans.get(ProductService.class).createInvoice(ids);       
		   
		/*    ProductRepository pRepo = Beans.get(ProductRepository.class);
		   
		    InvoiceRepository invRepo = Beans.get(InvoiceRepository.class);
		         System.out.println(invRepo.all().fetch());             
		     
		     CompanyRepository cmpRepo = Beans.get(CompanyRepository.class);
		     PartyRepository prtyRepo = Beans.get(PartyRepository.class);
		     Company cmpany = cmpRepo.all().fetchOne();
		     Party party = prtyRepo.all().fetchOne();
		    
		 
		     
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
		         invoice.setCompany(cmpany);
		         invoice.setParty(party);
		         
		        //  pRepo.save(invoice);
*/		               
		  ActionViewBuilder actionViewBuilder = ActionView.define("Invoices")
			                                   .model(Invoice.class.getName())
			                                   .add("form","invoice-form")
			                                    .context("_showRecord",131);
	         
		   response.setView(actionViewBuilder.map());
		    // response.setValue("invoiceItems", invcLine);
	  }
}
