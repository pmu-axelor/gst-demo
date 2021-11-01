package com.axelor.gst.Controller;


import java.util.List;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Party;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class PartyController {
	
	public void primaryContact(ActionRequest request, ActionResponse response) {
		
		List<Contact> contact = request.getContext().asType(Invoice.class).getParty().getContact();
		
		for(Contact c:contact) {
			
			if(c.getType().equals("primary")) { 
			
			  response.setValue("partyContact", c);
		   }
		}
		
		List<Address> addresses = request.getContext().asType(Invoice.class).getParty().getAddress();
		
		for(Address address: addresses) {
			  if(address.getType().equals("invoice")) {
				  response.setValue("invoiceAddress", address);
				  
			  }
			  else if(address.getType().equals("default")) {
				  response.setValue("invoiceAddress", address);
				
			  }
			 
		}
		     
	} 
	
	public void shippingAddress(ActionRequest request, ActionResponse response) {
		List<Address> addresses = request.getContext().asType(Invoice.class).getParty().getAddress();
		
		   for(Address address: addresses) {
			    if(address.getType().equals("shipping")) {
			    	 response.setValue("shippingAddress", address);
			    }
			    else if(address.getType().equals("default")) {
			    	   response.setValue("shippingAddress", address);
			    }
		   }
	}
}
