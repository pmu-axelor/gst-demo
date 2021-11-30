package com.axelor.gst.service;

import java.util.List;
import com.axelor.gst.db.Invoice;

public interface ProductService {

	public Invoice createInvoice(List<Long> ids);

}
