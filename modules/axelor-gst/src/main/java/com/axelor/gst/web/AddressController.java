package com.axelor.gst.web;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.service.AddressService;
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

	public void setShippingAddress(ActionRequest request, ActionResponse response) {

		Invoice invoice = request.getContext().asType(Invoice.class);
		Beans.get(AddressService.class).getShippingAddresses(invoice);

		if (invoice.getUseInvoiceAddressAsShipping()) {
			response.setValue("shippingAddress", invoice.getInvoiceAddress());
			response.setAttr("shippingAddress", "hidden", true);
		} else {
			response.setAttr("shippingAddress", "hidden", false);
			response.setValue("shippingAddress", invoice.getShippingAddress());
		}

	}

}
