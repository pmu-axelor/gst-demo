package com.axelor.gst.Services;

import java.math.BigDecimal;
import java.util.List;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.google.inject.persist.Transactional;

public interface InvoiceService {
	
    public void getInvoiceItems(Invoice invoice);
    @Transactional
    public String setSequence() throws Exception;
    
}
