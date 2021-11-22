package com.axelor.gst.Controller;


import java.math.BigDecimal;
import java.util.List;

import com.axelor.gst.Services.InvoiceService;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.meta.schema.actions.ActionView.ActionViewBuilder;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

public class InvoiceController {
	
      public void generateSequence(ActionRequest request, ActionResponse response) {
    	 
    	  Invoice invoice = request.getContext().asType(Invoice.class);
    	  
    	  try {
    	    if(invoice.getStatus().equals("validated")) {
    	      if(invoice.getReference() == null) {
    	         String sequence = Beans.get(InvoiceService.class).setSequence();
    	           response.setValue("reference", sequence);
    	           
    	           System.out.println(" From invoice controller " + sequence);
    }
    	    }
    	  }catch (Exception e) {
			response.setError("Set sequence of invoice");
		}
    }
      
      public void setInvoiceItems(ActionRequest request, ActionResponse response) {
    	     
    	  Context context = request.getContext();
 		  Invoice invoice = context.asType(Invoice.class);
 	  // InvoiceLine invoiceLine = context.getParent().asType(InvoiceLine.class);
 		  
 		     Beans.get(InvoiceService.class).getInvoiceItems(invoice);
 		    
 		     response.setValue("netAmount", invoice.getNetAmount());
			 response.setValue("netIgst", invoice.getNetIgst());
			 response.setValue("netSgst", invoice.getNetSgst());
			 response.setValue("netCsgt", invoice.getNetCsgt());
			 response.setValue("grossAmount",invoice.getGrossAmount());
    	  
    	 /* List<InvoiceLine> invoiceItems = request.getContext().asType(Invoice.class).getInvoiceItems();
    	  
    	      Beans.get(InvoiceService.class).getInvoiceItems(invoiceItems);
    	     
    	      for(InvoiceLine in : invoiceItems) {
    	    	 response.setValue("netAmount", in.getNetAmount());
  				 response.setValue("netIgst", in.getIgst());
  				 response.setValue("netSgst", in.getSgst());
  				 response.setValue("netCsgt", in.getCgst());
  				 response.setValue("grossAmount",in.getGrossAmount());
    	      }*/
    	  
    		   
    			    /*response.setValue("netAmount", netValues[0]);
    				response.setValue("netIgst", netValues[1]);
    				response.setValue("netSgst", netValues[2]);
    				response.setValue("netCsgt", netValues[3]);
    				response.setValue("grossAmount", netValues[4]);*/
    		  
    	  
      }
   
   }
