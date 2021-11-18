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
	
	
	
	public void computeIgstSgstCgstAndGrossAmount(ActionRequest request, ActionResponse response) {
		
		 Context context = request.getContext();
		 Invoice invoice = context.getParent().asType(Invoice.class);
		 InvoiceLine invoiceLine = context.asType(InvoiceLine.class);
		
		  InvoiceLineService invoiceLineService = Beans.get(InvoiceLineService.class);
		  invoiceLineService.getIgstSgstCgstAndGrossAmount(invoice, invoiceLine);
		 
		  response.setValue("igst", invoiceLine.getIgst());
		  response.setValue("sgst", invoiceLine.getSgst());
		  response.setValue("cgst", invoiceLine.getCgst());
		  response.setValue("grossAmount", invoiceLine.getGrossAmount());
			
		
	}
	
/*	public void setSGSTnCGST(ActionRequest request, ActionResponse response) {
		
		Context context = request.getContext();
		Invoice invoice = context.getParent().asType(Invoice.class);
		InvoiceLine invoiceLine = context.asType(InvoiceLine.class);
		
	//	BigDecimal[] sgstAndcgst = Beans.get(InvoiceLineService.class).getSGSTnCGST(invoice, invoiceLine);
		
		 if(sgstAndcgst != null) {
			 response.setValue("sgst", sgstAndcgst[0]);
				response.setValue("cgst", sgstAndcgst[1]);
		 }
	}
	
	public void setGrossAmount(ActionRequest request, ActionResponse response) {
		
		Context context = request.getContext();
		Invoice invoice = context.getParent().asType(Invoice.class);
		InvoiceLine invoiceLine = context.asType(InvoiceLine.class);
		
		BigDecimal grossAmount = Beans.get(InvoiceLineService.class).getGrossAmount(invoice, invoiceLine);
		
		if(grossAmount != null) {
			      response.setValue("grossAmount", grossAmount);
		}
	}*/
}
