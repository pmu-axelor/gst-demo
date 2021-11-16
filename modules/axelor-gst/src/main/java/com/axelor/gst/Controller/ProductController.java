package com.axelor.gst.Controller;



import java.util.List;
import java.util.Map;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.repo.ProductRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.meta.schema.actions.ActionView.ActionViewBuilder;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

public class ProductController {
	
	   public void getInvoiceForm(ActionRequest request, ActionResponse response) {
		
		   System.out.println(request.getContext().entrySet());
		  
		  List<Long> ids = (List<Long>) request.getContext().get("_ids");
		   System.out.println(ids);
		   
		 ActionViewBuilder actionViewBuilder = ActionView.define("Invoices")
			                             .model(Invoice.class.getName())
			                             .add("form","invoice-form")
			                               .domain("self.id = :ids")
			                               .context("ids",ids.toString());
	         
		     response.setView(actionViewBuilder.map());
	     
	  }
}
