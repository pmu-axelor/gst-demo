package com.axelor.gst.services;

import java.math.BigDecimal;
import java.util.List;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.persist.Transactional;


public class InvoiceServiceImpl implements InvoiceService {

	@Override
	public void computeInvoices(Invoice invoice) {
		
		BigDecimal bigDecimal = new BigDecimal("0");
		BigDecimal netAmount = bigDecimal;
		BigDecimal netIgst = bigDecimal;
		BigDecimal netSgst = bigDecimal;
		BigDecimal netCsgt = bigDecimal;
		BigDecimal grossAmount = bigDecimal;
		
		 List<InvoiceLine> invoiceLine = invoice.getInvoiceItems();
		
		 for(InvoiceLine invcLine : invoiceLine ) {
			netAmount = netAmount.add(invcLine.getNetAmount());
			netIgst = netIgst.add(invcLine.getIgst());	
			netSgst = netSgst.add(invcLine.getSgst());
			netCsgt = netCsgt.add(invcLine.getCgst());	
			grossAmount = grossAmount.add(invcLine.getGrossAmount());
		 }
		  
		  invoice.setNetAmount(netAmount);
		  invoice.setNetIgst(netIgst);
		  invoice.setNetSgst(netSgst);
		  invoice.setNetCsgt(netCsgt);
		  invoice.setGrossAmount(grossAmount);
		
		
   }

	@Override
	@Transactional
	public String setSequence() throws Exception {
		String sequence = "";
		
		MetaModelRepository metaModel = Beans.get(MetaModelRepository.class);
		MetaModel m = metaModel.findByName("Invoice");
		SequenceRepository seqRepo = Beans.get(SequenceRepository.class);
		
		Sequence seq = seqRepo.all().filter("self.model = ? ",m).fetchOne();
		
		String prefix = seq.getPrefix();
		String suffix = seq.getSuffix();
		Integer padding = seq.getPadding();
		
		if(seq.getNextNumber() == null) {
			      seq.setNextNumber("1");
			     System.out.println(seq.getNextNumber());
			    Integer nextNumber = Integer.parseInt( seq.getNextNumber());
		    	String  num = String.format("%0" + padding + "d", nextNumber);
			  
			  if(!StringUtils.isBlank(suffix)) {
					 sequence = prefix+num+suffix;
				}else {
					    sequence = prefix+num;
				 }
			    nextNumber = nextNumber+1;
				seq.setNextNumber(nextNumber.toString());
				seqRepo.save(seq);
		}
	
		
		
     else if(seq.getNextNumber() != null) {
		      Integer nextNumber = Integer.parseInt(seq.getNextNumber());
		      String  num = String.format("%0" + padding + "d", nextNumber);
		if(!StringUtils.isBlank(suffix)) {
			 sequence = prefix+num+suffix;
		}else {
			    sequence = prefix+num;
		 }
		
		  nextNumber = nextNumber+1;
		  seq.setNextNumber(nextNumber.toString());
		  seqRepo.save(seq);
	}
		
		return sequence;
	}


}
