package com.axelor.gst.Controller;

import java.util.List;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class AddressController {
	public void primaryContact(ActionRequest request, ActionResponse response) {

		Invoice invoice = request.getContext().asType(Invoice.class);
		response.setValue("partyContact",  getPrimaryContact(invoice));

		List<Address> addresses = invoice.getParty().getAddress();

		for (Address address : addresses) {
			if (address.getType().equals("invoice")) {
				response.setValue("invoiceAddress", address);

			} else if (address.getType().equals("default")) {
				response.setValue("invoiceAddress", address);
              }else {
            	       response.setValue("invoiceAddress", null);
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

	public void shippingAddress(ActionRequest request, ActionResponse response) {

		Invoice invoice = request.getContext().asType(Invoice.class);
		Boolean isTrue = invoice.getUseInvoiceAddressAsShipping();

		Address invoiceAddres = invoice.getInvoiceAddress();
		// System.out.println(invoiceAddres);
		
	
		List<Address> addresses = invoice.getParty().getAddress();
         //  System.out.println(addresses); 
		 
		if (!isTrue) {
	    for (Address address : addresses) {
		 if (address.getType().equals("shipping")) {
			 response.setValue("shippingAddress", address);
			} else if (address.getType().equals("default")) {
			  response.setValue("shippingAddress", address);
			} else {
			  response.setValue("shippingAddress", null);
				 } }

		} else {
			response.setValue("shippingAddress", invoiceAddres);
			response.setAttr("shippingAddress", "hidden", true);

		}

	}
}
