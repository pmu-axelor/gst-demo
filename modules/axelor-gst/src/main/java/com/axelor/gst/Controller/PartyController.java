package com.axelor.gst.Controller;


import java.math.BigDecimal;
import java.util.List;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
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
	
	public void setIGST(ActionRequest request, ActionResponse response) {
		
		   Invoice invoice = request.getContext().getParent().asType(Invoice.class);
		   Address invoiceAddress = invoice.getInvoiceAddress();
		   Address companyAddress = invoice.getCompany().getAddress();
		   
		   InvoiceLine asType = request.getContext().asType(InvoiceLine.class);
		       BigDecimal gstRate = asType.getGstRate();
		       BigDecimal netAmount = asType.getNetAmount();
		   
	       BigDecimal igst = gstRate.multiply(netAmount);
	       
		   System.out.println(netAmount);
		   System.out.println(gstRate);
		   System.out.println(invoiceAddress.getState().getName() + " " + companyAddress.getState().getName()); 
		   System.out.println(igst + "\n");
		   
		  if(!(invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			      
			       response.setValue("igst", igst);
		   }
	}
	
	public void setSGSTnCGST(ActionRequest request, ActionResponse response) {
		
		Invoice asType = request.getContext().getParent().asType(Invoice.class);
		Address invoiceAddress = asType.getInvoiceAddress();
		Address companyAddress = asType.getCompany().getAddress();
		
		 InvoiceLine asType2 = request.getContext().asType(InvoiceLine.class);
		 BigDecimal gstRate = asType2.getGstRate();
		 BigDecimal netAmount = asType2.getNetAmount();
		 
		 BigDecimal divisior = new BigDecimal("2");
		 
	     BigDecimal sgst	=  (gstRate.multiply(netAmount)).divide(divisior); /*netAmount.multiply(gstRate.divide(divisior))*/
	     BigDecimal cgst	= (gstRate.multiply(netAmount)).divide(divisior);
	     
	     System.out.println("This is from setSgstnGst method " + "\n" + gstRate);
	     System.out.println(netAmount);
	     System.out.println(sgst);
	     System.out.println(cgst);
	     System.out.println(invoiceAddress.getState().getName() + "---" + companyAddress.getState().getName() );
	     
	     if((invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
	    	  response.setValue("sgst", sgst);
	    	  response.setValue("cgst", cgst);
	     }
	
	}
	
	public void setGrossAmount(ActionRequest request, ActionResponse response) {
		
		Invoice asType = request.getContext().getParent().asType(Invoice.class);
		Address invoiceAddress = asType.getInvoiceAddress();
		Address companyAddress = asType.getCompany().getAddress();
		
		InvoiceLine asType2 = request.getContext().asType(InvoiceLine.class);
		BigDecimal gstRate = asType2.getGstRate();
		BigDecimal netAmount = asType2.getNetAmount();
		BigDecimal igst = asType2.getIgst();
		BigDecimal sgst = asType2.getSgst();
		BigDecimal cgst = asType2.getCgst();
		
		System.out.println("this is from setGrossAmount method " + "\n" + gstRate);
		System.out.println(netAmount);
		System.out.println(igst);
		System.out.println(sgst);
		System.out.println(cgst);
		System.out.println(invoiceAddress.getState().getName()+ " : " + companyAddress.getState().getName());
		
		
		if(!(invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			BigDecimal grossAmount = netAmount.add(igst);
			 response.setValue("grossAmount", grossAmount);
		}
		else {
			   BigDecimal grossAmount = netAmount.add(sgst.add(cgst));
			   response.setValue("grossAmount", grossAmount);
		}
	}
	
}
