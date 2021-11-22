package com.axelor.gst.module;

import com.axelor.app.AxelorModule;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.services.AddressService;
import com.axelor.gst.services.AddressServiceImpl;
import com.axelor.gst.services.InvoiceLineService;
import com.axelor.gst.services.InvoiceLineServiceImpl;
import com.axelor.gst.services.InvoiceService;
import com.axelor.gst.services.InvoiceServiceImpl;
import com.axelor.gst.services.PartyService;
import com.axelor.gst.services.PartyServiceImpl;
import com.axelor.gst.services.ProductService;
import com.axelor.gst.services.ProductServiceImpl;

public class Modules extends AxelorModule{
 
	@Override
	protected void configure() {
		bind(PartyService.class).to(PartyServiceImpl.class);
	    bind(ProductService.class).to(ProductServiceImpl.class);
		bind(InvoiceLineService.class).to(InvoiceLineServiceImpl.class);
		bind(InvoiceService.class).to(InvoiceServiceImpl.class);
		bind(AddressService.class).to(AddressServiceImpl.class);
	}
}
