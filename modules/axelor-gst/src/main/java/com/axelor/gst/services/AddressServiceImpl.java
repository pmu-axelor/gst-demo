package com.axelor.gst.services;

import java.util.List;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;

public class AddressServiceImpl implements AddressService {
   
	@Override
	public void getInvoiceAddresses(Invoice invoice) {
		
		List<Address>  addresses = invoice.getParty().getAddress();
		//Boolean isTrue = invoice.getUseInvoiceAddressAsShipping();
		  invoice.setPartyContact(getPrimaryContact(invoice));
		
		 for(Address address : addresses ) {
			 if(address.getType().equals("default")) {
				  invoice.setInvoiceAddress(address);
			 }
			 else if(address.getType().equals("invoice")) {
				      invoice.setInvoiceAddress(address);  
			 } else {
				     invoice.setInvoiceAddress(null);
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
		   
		  }

	@Override
	public void getShippingAddresses(Invoice invoice) {
		
		List<Address>  addresses = invoice.getParty().getAddress();
		 if(addresses != null) {
			 for(Address address : addresses) {
				   if(address.getType().equals("default")) {
					    invoice.setShippingAddress(address);
				   }
				   else if(address.getType().equals("shipping")) {
					       invoice.setShippingAddress(address);
				   }else {
					        invoice.setShippingAddress(null);
				   }
			 }
		 }
		
	}
}
