package com.axelor.gst.Services;

import java.math.BigDecimal;
import java.util.List;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.persist.Transactional;

public class InvoiceServiceImpl implements InvoiceService {

	@Override
	public void getInvoiceItems(List<InvoiceLine> invoiceItems) {
		
		BigDecimal bigDecimal = new BigDecimal("0");
		BigDecimal netAmount = bigDecimal;
		BigDecimal netIgst = bigDecimal;
		BigDecimal netSgst = bigDecimal;
		BigDecimal netCsgt = bigDecimal;
		BigDecimal grossAmount = bigDecimal;
		
		for (InvoiceLine in : invoiceItems) {
			netAmount = netAmount.add(in.getNetAmount());
			in.setNetAmount(netAmount);
			netIgst = netIgst.add(in.getIgst());
			in.setIgst(netIgst);
			netSgst = netSgst.add(in.getSgst());
			in.setSgst(netSgst);
			netCsgt = netCsgt.add(in.getCgst());
			in.setCgst(netCsgt);
			grossAmount = grossAmount.add(in.getGrossAmount());
			in.setGrossAmount(grossAmount);

		}
		
		
		
		/*BigDecimal[] totalValues = new BigDecimal[5];
		totalValues[0] = netAmount;
		totalValues[1] = netIgst;
		totalValues[2] = netSgst;
		totalValues[3] = netCsgt;
		totalValues[4] = grossAmount;*/
		  
	//	return totalValues;
		

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
			  System.out.println(seq.getNextNumber());
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
