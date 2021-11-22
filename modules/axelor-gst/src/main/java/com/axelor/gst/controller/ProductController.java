package com.axelor.gst.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.axelor.gst.db.Company;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.repo.CompanyRepository;
import com.axelor.gst.db.repo.InvoiceRepository;
import com.axelor.gst.db.repo.PartyRepository;
import com.axelor.gst.db.repo.ProductRepository;
import com.axelor.gst.services.ProductService;
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
		    
		     Invoice invoice = Beans.get(ProductService.class).createInvoice(ids);
		     System.out.println(invoice);
               
		      ActionViewBuilder actionViewBuilder = ActionView.define("Invoices")
			                                        .model(Invoice.class.getName())
			                                        .add("form","invoice-form")
			                                        .context("_showRecord",invoice.getId());
	       response.setView(actionViewBuilder.map());
		    // response.setValue("invoiceItems", invcLine);
	  }
}
