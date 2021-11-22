package com.axelor.gst.services;

import com.axelor.gst.db.Invoice;

public interface AddressService {
	
	public void getInvoiceAddresses(Invoice invoice) ;
	public void getShippingAddresses(Invoice invoice);
	
}
