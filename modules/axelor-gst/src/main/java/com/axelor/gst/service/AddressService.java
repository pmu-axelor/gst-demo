package com.axelor.gst.service;

import com.axelor.gst.db.Invoice;

public interface AddressService {

	public void getInvoiceAddresses(Invoice invoice);

	public void getShippingAddresses(Invoice invoice);

}
