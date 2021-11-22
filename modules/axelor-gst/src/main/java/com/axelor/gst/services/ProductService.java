package com.axelor.gst.services;

import java.util.List;

import com.axelor.gst.db.Invoice;
import com.google.inject.persist.Transactional;

public interface ProductService {
	
   @Transactional	
   public Invoice createInvoice(List<Integer> ids);
   
}
