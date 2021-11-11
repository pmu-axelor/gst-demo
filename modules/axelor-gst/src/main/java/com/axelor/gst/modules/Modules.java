package com.axelor.gst.modules;

import com.axelor.app.AxelorModule;
import com.axelor.gst.InvoiceService.InvoiceServiceInter;
import com.axelor.gst.InvoiceService.InvoiceServiceInterImpl;
import com.axelor.gst.PartyService.PartyServiceInter;
import com.axelor.gst.PartyService.PartyServiceInterImpl;

public class Modules extends AxelorModule{
 
	@Override
	protected void configure() {
		bind(PartyServiceInter.class).to(PartyServiceInterImpl.class);
		bind(InvoiceServiceInter.class).to(InvoiceServiceInterImpl.class);
	}
}
