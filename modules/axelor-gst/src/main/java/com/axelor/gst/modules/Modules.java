package com.axelor.gst.modules;

import com.axelor.app.AxelorModule;
import com.axelor.gst.Services.AddressService;
import com.axelor.gst.Services.AddressServiceImpl;
import com.axelor.gst.Services.InvoiceLineService;
import com.axelor.gst.Services.InvoiceLineServiceImpl;
import com.axelor.gst.Services.InvoiceService;
import com.axelor.gst.Services.InvoiceServiceImpl;
import com.axelor.gst.Services.PartyService;
import com.axelor.gst.Services.PartyServiceImpl;
import com.axelor.gst.Services.ProductService;
import com.axelor.gst.Services.ProductServiceImpl;

import com.axelor.gst.db.Invoice;

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
