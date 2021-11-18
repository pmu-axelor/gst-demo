package com.axelor.gst.Services;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.math.BigDecimal;

public interface InvoiceLineService {

	public void getIgstSgstCgstAndGrossAmount(Invoice invoice,InvoiceLine invoiceLine);
	
}
