package com.axelor.gst.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import com.axelor.gst.Services.PartyService;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.persist.Transactional;

public class PartyController {
   
	public void getSequenceSet(ActionRequest request, ActionResponse response) {

		Party party = request.getContext().asType(Party.class);
		try {
			if (party.getReference() == null) {
				String sequence = Beans.get(PartyService.class).setSequence();
				response.setValue("reference", sequence);
				System.out.println(sequence);

			}
		} catch (Exception e) {
			response.setError("Set sequence for party");
		}

	}

}
