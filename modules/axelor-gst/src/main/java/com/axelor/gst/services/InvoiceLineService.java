package com.axelor.gst.services;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.math.BigDecimal;

public interface InvoiceLineService {

	public void computeInvoiceLinesItems(Invoice invoice,InvoiceLine invoiceLine);
	
}
