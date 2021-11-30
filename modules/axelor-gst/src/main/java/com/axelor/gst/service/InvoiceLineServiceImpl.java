package com.axelor.gst.service;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.math.BigDecimal;

public class InvoiceLineServiceImpl implements InvoiceLineService {

	@Override
	public void computeInvoiceLinesItems(Invoice invoice, InvoiceLine invoiceLine) throws Exception {

		Address invoiceAddress = invoice.getInvoiceAddress();
		Address companyAddress = invoice.getCompany().getAddress();

		BigDecimal gstRate = invoiceLine.getGstRate();
		BigDecimal netAmount = invoiceLine.getPrice().multiply(BigDecimal.valueOf(invoiceLine.getQty()));

		BigDecimal gstDivisior = new BigDecimal("100");
		BigDecimal divisior = new BigDecimal("2");

		invoiceLine.setNetAmount(netAmount);
		if (invoice.getParty() == null) {
			throw new Exception("set party");
		}  else {
			if (invoiceAddress.getState().equals(companyAddress.getState())) {
				BigDecimal sgstAndcgst = ((gstRate.divide(gstDivisior)).multiply(netAmount)).divide(divisior);
				invoiceLine.setCgst(sgstAndcgst);
				invoiceLine.setSgst(sgstAndcgst);
				invoiceLine.setGrossAmount(netAmount.add(sgstAndcgst.add(sgstAndcgst)));
			} else {
				BigDecimal igstCal = (gstRate.divide(gstDivisior)).multiply(netAmount);
				invoiceLine.setIgst(igstCal);
				invoiceLine.setGrossAmount(netAmount.add(igstCal));
			}
		}
	}

}