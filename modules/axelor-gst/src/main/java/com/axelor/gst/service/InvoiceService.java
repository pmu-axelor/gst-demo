package com.axelor.gst.service;

import com.axelor.gst.db.Invoice;

public interface InvoiceService {

	public void computeInvoices(Invoice invoice);

	public String setSequence() throws Exception;

}
