package com.axelor.gst.modules;

import com.axelor.app.AxelorModule;
import com.axelor.gst.Services.InvoiceLineService;
import com.axelor.gst.Services.InvoiceLineServiceImpl;
import com.axelor.gst.Services.PartyService;
import com.axelor.gst.Services.PartyServiceImpl;
import com.axelor.gst.Services.ProductService;
import com.axelor.gst.Services.ProductServiceImpl;
import com.axelor.gst.Services.SequenceService;
import com.axelor.gst.Services.SequenceServiceImpl;

public class Modules extends AxelorModule{
 
	@Override
	protected void configure() {
		bind(PartyService.class).to(PartyServiceImpl.class);
		bind(SequenceService.class).to(SequenceServiceImpl.class);
		bind(ProductService.class).to(ProductServiceImpl.class);
		bind(InvoiceLineService.class).to(InvoiceLineServiceImpl.class);
	}
}
