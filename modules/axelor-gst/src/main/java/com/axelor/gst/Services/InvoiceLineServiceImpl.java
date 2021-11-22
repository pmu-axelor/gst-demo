package com.axelor.gst.Services;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.math.BigDecimal;

public class InvoiceLineServiceImpl implements InvoiceLineService {

	@Override
	public void getIgstSgstCgstAndGrossAmount(Invoice invoice, InvoiceLine invoiceLine) {
		
		Address invoiceAddress = invoice.getInvoiceAddress();
		Address companyAddress = invoice.getCompany().getAddress();
		
		BigDecimal gstRate = invoiceLine.getGstRate();
		BigDecimal netAmount = invoiceLine.getNetAmount();
	
		BigDecimal gstDivisior = new BigDecimal("100");
		BigDecimal divisior = new BigDecimal("2");

		 if((invoiceAddress.getState()).equals((companyAddress.getState()))) {
			  BigDecimal sgstAndcgst = ((gstRate.divide(gstDivisior)).multiply(netAmount)).divide(divisior);
		         invoiceLine.setCgst(sgstAndcgst);
		         invoiceLine.setSgst(sgstAndcgst);
		         invoiceLine.setGrossAmount(netAmount.add(sgstAndcgst.add(sgstAndcgst)));
    	}

		 else if(!(invoiceAddress.getState()).equals((companyAddress.getState()))) {
		           BigDecimal igstCal = (gstRate.divide(gstDivisior)).multiply(netAmount);
				    invoiceLine.setIgst(igstCal);
				    invoiceLine.setGrossAmount(netAmount.add(igstCal));
			}
		
      /*if (!(invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			
			 grossAmount = netAmount.add(igstCal);
			  invoiceLine.setGrossAmount(grossAmount);
			
		}*//*else if((invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			 grossAmount = netAmount.add(sgstAndcgst.add(sgstAndcgst));
			 invoiceLine.setGrossAmount(grossAmount);
		}*/
		
		//invoiceLine.setIgst(igstCal);
		/*invoiceLine.setSgst(sgstAndcgst);
		invoiceLine.setCgst(sgstAndcgst);
		invoiceLine.setGrossAmount(grossAmount);*/
		
		
		
	}

	/*@Override
	public BigDecimal[] getSGSTnCGST(Invoice invoice, InvoiceLine invoiceLine) {
		
		Address invoiceAddress = invoice.getInvoiceAddress();
		Address companyAddress = invoice.getCompany().getAddress();
		
		BigDecimal gstRate = invoiceLine.getGstRate();
		BigDecimal netAmount = invoiceLine.getNetAmount();
		
		
		BigDecimal divisior = new BigDecimal("2");
		BigDecimal gstDivisior = new BigDecimal("100");
		
		BigDecimal sgst = ((gstRate.divide(gstDivisior)).multiply(netAmount)).divide(divisior);
		                                                     netAmount.multiply(gstRate.divide(divisior)) 
		BigDecimal cgst = ((gstRate.divide(gstDivisior)).multiply(netAmount)).divide(divisior);
		
		BigDecimal[] arry = new BigDecimal[2];
		  arry[0] = sgst;
		  arry[1] = cgst;
		
		if ((invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			    return arry;
		}
		
	
		
		return null;
	}

	@Override
	public BigDecimal getGrossAmount(Invoice invoice, InvoiceLine invoiceLine) {
		 
		Address invoiceAddress = invoice.getInvoiceAddress();
		Address companyAddress = invoice.getCompany().getAddress();
        
		BigDecimal gstRate = invoiceLine.getGstRate();
		BigDecimal netAmount = invoiceLine.getNetAmount();
		BigDecimal igst = invoiceLine.getIgst();
		BigDecimal sgst = invoiceLine.getSgst();
		BigDecimal cgst = invoiceLine.getCgst();
		
		if (!(invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			
			BigDecimal grossAmount = netAmount.add(igst);
			  return grossAmount;
			
		}else if((invoiceAddress.getState().getName()).equals((companyAddress.getState().getName()))) {
			BigDecimal grossAmount = netAmount.add(sgst.add(cgst));
			   return grossAmount;
		}
		
		return null;
	}*/

}
