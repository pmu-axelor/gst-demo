package com.axelor.gst.web;

import java.util.List;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.service.ProductService;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.meta.schema.actions.ActionView.ActionViewBuilder;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class ProductController {

	@SuppressWarnings("unchecked")
	public void getInvoiceForm(ActionRequest request, ActionResponse response) {

		List<Integer> ids = (List<Integer>) request.getContext().get("_ids");
		Invoice invoice = Beans.get(ProductService.class).createInvoice(ids);

		ActionViewBuilder actionViewBuilder = ActionView.define("Invoices").model(Invoice.class.getName())
				.add("form", "invoice-form").context("_showRecord", invoice.getId());
		response.setView(actionViewBuilder.map());

	}
}
