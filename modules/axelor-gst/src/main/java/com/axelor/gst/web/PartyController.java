package com.axelor.gst.web;

import com.axelor.gst.service.PartyService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class PartyController {

	public void getSequenceSet(ActionRequest request, ActionResponse response) {

		try {
			if (request.getContext().get("reference") == null) {
				String sequence = Beans.get(PartyService.class).setSequence();
				response.setValue("reference", sequence);

			}
		} catch (Exception e) {
			response.setError(e.getMessage());
		}

	}

}
