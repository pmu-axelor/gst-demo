package com.axelor.gst.Services;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.PartyRepository;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.persist.Transactional;


public class PartyServiceImpl implements PartyService {

	@Override
	@Transactional
	public String setSequence() throws Exception{
		
		
		 MetaModelRepository metaModel = Beans.get(MetaModelRepository.class);
		 MetaModel m = metaModel.findByName("Party");
		 SequenceRepository seqRepo = Beans.get(SequenceRepository.class);
		 
		 Sequence seq = seqRepo.all().filter("self.model = ?", m).fetchOne();
		 
		  String sequence = "";
		  
		
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
	    
	 /* return sequence;	
	    }*/
   }
