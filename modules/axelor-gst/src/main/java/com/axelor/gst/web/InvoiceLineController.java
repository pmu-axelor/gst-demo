package com.axelor.gst.web;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.service.InvoiceLineService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

public class InvoiceLineController {

	public void computeInvoiceLine(ActionRequest request, ActionResponse response) {

		Context context = request.getContext();
		Invoice invoice = context.getParent().asType(Invoice.class);
		InvoiceLine invoiceLine = context.asType(InvoiceLine.class);

		InvoiceLineService invoiceLineService = Beans.get(InvoiceLineService.class);
		try {

			invoiceLineService.computeInvoiceLinesItems(invoice, invoiceLine);

		} catch (Exception e) {
			response.setError(e.getMessage());
		}

		if (invoice.getInvoiceAddress() != null) {
			response.setValue("igst", invoiceLine.getIgst());
			response.setValue("sgst", invoiceLine.getSgst());
			response.setValue("cgst", invoiceLine.getCgst());
			response.setValue("grossAmount", invoiceLine.getGrossAmount());
		}

	}

}
