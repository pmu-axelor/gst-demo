package com.axelor.gst.modules;

import com.axelor.app.AxelorModule;
import com.axelor.gst.PartyService.PartyServiceInter;
import com.axelor.gst.PartyService.PartyServiceInterImpl;

public class PartyModule extends AxelorModule{
  
	@Override
	protected void configure() {
		bind(PartyServiceInter.class).to(PartyServiceInterImpl.class);
	}
}
