package com.axelor.gst.Controller;

import java.math.BigDecimal;

import com.axelor.gst.Services.InvoiceLineService;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

public class InvoiceLineController {
	
	
	
	public void setIGST(ActionRequest request, ActionResponse response) {
		
		Context context = request.getContext();
		Invoice invoice = context.getParent().asType(Invoice.class);
		InvoiceLine invoiceLine = context.asType(InvoiceLine.class);
		
		BigDecimal igst = Beans.get(InvoiceLineService.class).getIGST(invoice, invoiceLine);
		 
		if(igst != null) {
			response.setValue("igst", igst);
		}
		
	}
	
	
}
