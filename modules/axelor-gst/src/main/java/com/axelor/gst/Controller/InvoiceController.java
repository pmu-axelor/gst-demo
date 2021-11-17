package com.axelor.gst.Controller;


import com.axelor.gst.Services.SequenceService;
import com.axelor.gst.db.Invoice;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.meta.schema.actions.ActionView.ActionViewBuilder;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class InvoiceController {
	
      public void generateSequence(ActionRequest request, ActionResponse response) {
    	 
    	  Invoice invoice = request.getContext().asType(Invoice.class);
    	  
    	  try {
    	    if(invoice.getStatus().equals("validated")) {
    	      if(invoice.getReference() == null) {
    	         String sequence = Beans.get(SequenceService.class).setSequence();
    	           response.setValue("reference", sequence);
    	           
    	           System.out.println(" From invoice controller " + sequence);
    }
    	    }
    	  }catch (Exception e) {
			response.setError("Set sequence of invoice");
		}
    	 }
   
   }
