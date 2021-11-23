package com.axelor.gst.controller;

import java.math.BigDecimal;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.services.InvoiceLineService;
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
		  invoiceLineService.computeInvoiceLinesItems(invoice, invoiceLine);
			 
		  response.setValue("igst", invoiceLine.getIgst());
		  response.setValue("sgst", invoiceLine.getSgst());
		  response.setValue("cgst", invoiceLine.getCgst());
		  response.setValue("grossAmount", invoiceLine.getGrossAmount());
		  
		
		  
	}

}
