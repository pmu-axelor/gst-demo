package com.axelor.gst.Services;

import java.util.List;

import com.google.inject.persist.Transactional;

public interface ProductService {
	
   @Transactional	
   public void createInvoice(List<Integer> ids);
   
}
