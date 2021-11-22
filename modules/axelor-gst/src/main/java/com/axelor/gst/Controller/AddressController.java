package com.axelor.gst.Controller;

import java.util.List;

import com.axelor.gst.Services.AddressService;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class AddressController {

	public void setInvoiceAddresses(ActionRequest request, ActionResponse response) {

		Invoice invoice = request.getContext().asType(Invoice.class);
		  
		Beans.get(AddressService.class).getInvoiceAddresses(invoice);
		 
		response.setValue("partyContact", invoice.getPartyContact());
		    
	    response.setValue("invoiceAddress", invoice.getInvoiceAddress());
	    
	}
	    
	  public void setShippingAddress(ActionRequest request, ActionResponse response)  {
		  
		  Invoice invoice = request.getContext().asType(Invoice.class);
		  Beans.get(AddressService.class).getShippingAddresses(invoice);
		  
	     if(invoice.getUseInvoiceAddressAsShipping() == true) {
	    	     response.setValue("shippingAddress", invoice.getInvoiceAddress());
	    	     response.setAttr("shippingAddress", "hidden", true);
	    }else {
	    	    response.setAttr("shippingAddress", "hidden", false);
	    	    response.setValue("shippingAddress", invoice.getShippingAddress());
	    }
		
	  }	/*
			 * if(invoice.getUseInvoiceAddressAsShipping() == true) {
			 * response.setValue("shippingAddress", invoice.getInvoiceAddress());
			 * response.setAttr("shippingAddress", "hidden", true); }else {
			 * response.setAttr("shippingAddress", "hidden", false);
			 * response.setValue("shippingAddress", invoice.getShippingAddress()); }
			 */
		
		
		
		/*response.setValue("partyContact",  getPrimaryContact(invoice));

		List<Address> addresses = invoice.getParty().getAddress();
         
		for (Address address : addresses) {
			 if (address.getType().equals("default")) {
				    response.setValue("invoiceAddress", address);
           } else if (address.getType().equals("invoice")) {
				     response.setValue("invoiceAddress", address);
			}else {
            	       response.setValue("invoiceAddress", null);
            }

		}
		
	
		Boolean isTrue = invoice.getUseInvoiceAddressAsShipping();
             
		
        
        if(isTrue) {
        	      response.setValue("shippingAddress", invoiceAddres);
		          response.setAttr("shippingAddress", "hidden", true);
       
        }
	     for (Address address : addresses) {
	    	 if(isTrue) {
	    		 if(address.getType().equals("invoice")) {
       	          response.setValue("shippingAddress", address);}
		          response.setAttr("shippingAddress", "hidden", true);
         }else{
	      if (address.getType().equals("default")) {
			 response.setValue("shippingAddress", address);
			} else if (address.getType().equals("shipping")) {
			  response.setValue("shippingAddress", address);
			} else {
			       response.setValue("shippingAddress", null);
				 } 
	          response.setAttr("shippingAddress", "hidden", false);
	    	 }
	              
		  } 
        }
    
	    protected Contact getPrimaryContact(Invoice invoice) {
		List<Contact> contacts = invoice.getParty().getContact();
		for (Contact c : contacts) {
        	if (c.getType().equals("primary")) {
				return c;
			}
		}
		return null;
	   
	  }*/
	    
//}
}
