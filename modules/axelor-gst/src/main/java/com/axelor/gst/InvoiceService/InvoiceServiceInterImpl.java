package com.axelor.gst.InvoiceService;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.persist.Transactional;

public class InvoiceServiceInterImpl implements InvoiceServiceInter {

	@Override
	@Transactional
	public String setSequence() {
        
		String sequence = "";
		MetaModelRepository metaModel = Beans.get(MetaModelRepository.class);
		MetaModel m = metaModel.findByName("Invoice");
		SequenceRepository seqRepo = Beans.get(SequenceRepository.class);
		
		Sequence seq = seqRepo.all().filter("self.model = ? ",m).fetchOne();
		
		String prefix = seq.getPrefix();
		String suffix = seq.getSuffix();
		Integer padding = seq.getPadding();
		Integer nextNumber = Integer.parseInt(seq.getNextNumber());
		
		String num = String.format("%0" + padding + "d", nextNumber);
		
		if(!StringUtils.isBlank(suffix)) {
			 sequence = prefix+num+suffix;
		}else {
			    sequence = prefix+num;
		}
		
		nextNumber = nextNumber+1;
		seq.setNextNumber(nextNumber.toString());
		seqRepo.save(seq);
	
		return sequence;

}
}