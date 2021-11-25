package com.axelor.gst.web;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.service.InvoiceService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

public class InvoiceController {

	public void generateSequence(ActionRequest request, ActionResponse response) {

		Invoice invoice = request.getContext().asType(Invoice.class);

		try {
			if ((invoice.getStatus().equals("validated")) && (invoice.getReference() == null)) {

				String sequence = Beans.get(InvoiceService.class).setSequence();
				response.setValue("reference", sequence);
			}
		} catch (Exception e) {
			response.setError("Set sequence of invoice");
		}
	}

	public void computeInvoiceItems(ActionRequest request, ActionResponse response) {

		Context context = request.getContext();
		Invoice invoice = context.asType(Invoice.class);
		Beans.get(InvoiceService.class).computeInvoices(invoice);

		response.setValue("netAmount", invoice.getNetAmount());
		response.setValue("netIgst", invoice.getNetIgst());
		response.setValue("netSgst", invoice.getNetSgst());
		response.setValue("netCsgt", invoice.getNetCsgt());
		response.setValue("grossAmount", invoice.getGrossAmount());

	}

}
