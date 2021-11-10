package com.axelor.gst.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import com.axelor.gst.PartyService.PartyServiceInter;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.persist.Transactional;

public class PartyController {

	public void primaryContact(ActionRequest request, ActionResponse response) {

		List<Contact> contact = request.getContext().asType(Invoice.class).getParty().getContact();

		for (Contact c : contact) {

			if (c.getType().equals("primary")) {

				response.setValue("partyContact", c);
			}
			
		}

		List<Address> addresses = request.getContext().asType(Invoice.class).getParty().getAddress();

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

	public void shippingAddress(ActionRequest request, ActionResponse response) {

		Invoice asType = request.getContext().asType(Invoice.class);
		Boolean isTrue = asType.getUseInvoiceAddressAsShipping();

		Address invoiceAddres = asType.getInvoiceAddress();
		// System.out.println(invoiceAddres);
		
	
		List<Address> addresses = asType.getParty().getAddress();
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

	public void setIGST(ActionRequest request, ActionResponse response) {

		Invoice invoice = request.getContext().getParent().asType(Invoice.class);
		Address invoiceAddress = invoice.getInvoiceAddress();
		Address companyAddress = invoice.getCompany().getAddress();

		InvoiceLine asType = request.getContext().asType(InvoiceLine.class);
		BigDecimal gstRate = asType.getGstRate();
		BigDecimal netAmount = asType.getNetAmount();

		BigDecimal gstDivisior = new BigDecimal("100");

		BigDecimal igst = (gstRate.divide(gstDivisior)).multiply(netAmount);

		/*
		 * System.out.println(netAmount); System.out.println(gstRate);
		 * System.out.println(invoiceAddress.getState().getName() + " " +
		 * companyAddress.getState().getName()); System.out.println(igst + "\n");
		 */

		if (!(invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {

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
		BigDecimal gstDivisior = new BigDecimal("100");

		BigDecimal sgst = ((gstRate.divide(gstDivisior)).multiply(netAmount)).divide(divisior); /* netAmount.multiply(gstRate.divide(divisior)) */
		BigDecimal cgst = ((gstRate.divide(gstDivisior)).multiply(netAmount)).divide(divisior);

		/*
		 * System.out.println("This is from setSgstnGst method " + "\n" + gstRate);
		 * System.out.println(netAmount); System.out.println(sgst);
		 * System.out.println(cgst);
		 * System.out.println(invoiceAddress.getState().getName() + "---" +
		 * companyAddress.getState().getName() );
		 */

		if ((invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
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

		/*
		 * System.out.println("this is from setGrossAmount method " + "\n" + gstRate);
		 * System.out.println(netAmount); System.out.println(igst);
		 * System.out.println(sgst); System.out.println(cgst);
		 * System.out.println(invoiceAddress.getState().getName()+ " : " +
		 * companyAddress.getState().getName());
		 */

		if (!(invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			BigDecimal grossAmount = netAmount.add(igst);
			response.setValue("grossAmount", grossAmount);
		} else {
			BigDecimal grossAmount = netAmount.add(sgst.add(cgst));
			response.setValue("grossAmount", grossAmount);
		}
	}

	public void setInvoiceItems(ActionRequest request, ActionResponse response) {
		
		
		List<InvoiceLine> invoiceItems = request.getContext().asType(Invoice.class).getInvoiceItems();
		
		BigDecimal netAmount = new BigDecimal("0");
		BigDecimal netIgst = new BigDecimal("0");
		BigDecimal netSgst = new BigDecimal("0");
		BigDecimal netCsgt = new BigDecimal("0");
		BigDecimal grossAmount = new BigDecimal("0");
		   for (InvoiceLine in : invoiceItems) {
                     netAmount = netAmount.add(in.getNetAmount());
			         netIgst = netIgst.add(in.getIgst());
			         netSgst = netSgst.add(in.getSgst());
                     netCsgt = netCsgt.add(in.getCgst());
                     grossAmount = grossAmount.add(in.getGrossAmount());
			
		}
		   response.setValue("netAmount", netAmount);
			response.setValue("netIgst", netIgst);
			response.setValue("netSgst", netSgst);
			response.setValue("netCsgt", netCsgt);
			response.setValue("grossAmount", grossAmount);
		
	}
	
	
	public void getSequence(ActionRequest request, ActionResponse response) {
		    
		String sequence = Beans.get(PartyServiceInter.class).setSequence();
		 
		 response.setValue("reference", sequence);
		 System.out.println(sequence);
		
	       	
	
	}
	
}
