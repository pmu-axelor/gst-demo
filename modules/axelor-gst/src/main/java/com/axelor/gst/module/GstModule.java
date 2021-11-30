package com.axelor.gst.module;

import com.axelor.app.AxelorModule;
import com.axelor.gst.service.AddressService;
import com.axelor.gst.service.AddressServiceImpl;
import com.axelor.gst.service.InvoiceLineService;
import com.axelor.gst.service.InvoiceLineServiceImpl;
import com.axelor.gst.service.InvoiceService;
import com.axelor.gst.service.InvoiceServiceImpl;
import com.axelor.gst.service.PartyService;
import com.axelor.gst.service.PartyServiceImpl;
import com.axelor.gst.service.ProductService;
import com.axelor.gst.service.ProductServiceImpl;

public class GstModule extends AxelorModule {

	@Override
	protected void configure() {
		bind(PartyService.class).to(PartyServiceImpl.class);
		bind(ProductService.class).to(ProductServiceImpl.class);
		bind(InvoiceLineService.class).to(InvoiceLineServiceImpl.class);
		bind(InvoiceService.class).to(InvoiceServiceImpl.class);
		bind(AddressService.class).to(AddressServiceImpl.class);
	}
}
