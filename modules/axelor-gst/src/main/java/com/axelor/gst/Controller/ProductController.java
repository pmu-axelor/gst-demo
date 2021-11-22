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
	  
	   @SuppressWarnings("unchecked")
	   @Transactional
	    public void getInvoiceForm(ActionRequest request, ActionResponse response) {
		
		    //  System.out.println(request.getContext().entrySet());
		     
		  
		    
			 List<Integer> ids = (List<Integer>) request.getContext().get("_ids");
		     System.out.println(ids);
		     
		     Invoice invoice = new Invoice();
		     
		     Beans.get(ProductService.class).createInvoice(ids,invoice);
				/*
				 * ProductRepository pRepo = Beans.get(ProductRepository.class);
				 * 
				 * InvoiceRepository invRepo = Beans.get(InvoiceRepository.class);
				 * //System.out.println(invRepo.all().fetch());
				 * 
				 * CompanyRepository cmpRepo = Beans.get(CompanyRepository.class);
				 * PartyRepository prtyRepo = Beans.get(PartyRepository.class); Company cmpany =
				 * cmpRepo.all().fetchOne(); Party party = prtyRepo.all().fetchOne();
				 * 
				 * 
				 * 
				 * Invoice invoice = new Invoice(); Product product = new Product();
				 * invoice.setInvoiceAddress(party.getAddress().get(0));
				 * 
				 * invoice.setStatus("draft"); invoice.setCompany(cmpany);
				 * invoice.setParty(party); invoice.setPartyContact(party.getContact().get(0));
				 * // invoice.setDates(); if(ids != null) { for(Integer l : ids){ product =
				 * pRepo.find(Long.valueOf(l)); System.out.println(product); InvoiceLine
				 * invcLine = new InvoiceLine(); invcLine.setProduct(product);
				 * invcLine.setHsbn(product.getHsbn());
				 * invcLine.setItem(product.getCode()+"-"+product.getName());
				 * invcLine.setGstRate(product.getGstRate());
				 * invcLine.setPrice(product.getSalePrice());
				 * 
				 * invoice.addInvoiceItem(invcLine); }
				 * 
				 * //invoice.setInvoiceItems(lines);
				 * 
				 * invRepo.save(invoice); }
				 */
		    //  System.out.println(product.getId());
		      System.out.println(invoice);
               
		      ActionViewBuilder actionViewBuilder = ActionView.define("Invoices")
			                                        .model(Invoice.class.getName())
			                                        .add("form","invoice-form")
			                                        .context("_showRecord",invoice.getId());
	       response.setView(actionViewBuilder.map());
		    // response.setValue("invoiceItems", invcLine);
	  }
}
