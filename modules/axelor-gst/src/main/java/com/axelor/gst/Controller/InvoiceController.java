package com.axelor.gst.Controller;

import com.axelor.gst.InvoiceService.InvoiceServiceInter;
import com.axelor.gst.PartyService.PartyServiceInter;
import com.axelor.gst.db.Invoice;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class InvoiceController {
	
      public void generateSequence(ActionRequest request, ActionResponse response) {
    	 
    	  Invoice invoice = request.getContext().asType(Invoice.class);
    	  
    	  try {
    	    if(invoice.getStatus().equals("validated")) {
    	      if(invoice.getReference() == null) {
    	         String sequence = Beans.get(InvoiceServiceInter.class).setSequence();
    	           response.setValue("reference", sequence);
    	           
    	           System.out.println(" From invoice controller " + sequence);
    }
    	    }
    	  }catch (Exception e) {
			response.setError("Set sequence of invoice");
		}
    	 }
   }
