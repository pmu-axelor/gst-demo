package com.axelor.gst.Services;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.math.BigDecimal;

public interface InvoiceLineService {

	public BigDecimal getIGST(Invoice invoice,InvoiceLine invoiceLine);
	public BigDecimal[] getSGSTnCGST(Invoice invoice,InvoiceLine invoiceLine);
	public BigDecimal getGrossAmount(Invoice invoice,InvoiceLine invoiceLine);
	
}
