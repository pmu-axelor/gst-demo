package com.axelor.gst.PartyService;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.PartyRepository;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.persist.Transactional;


public class PartyServiceInterImpl implements PartyServiceInter {

	@Override
	@Transactional
	public String setSequence() {
		
		
		 MetaModelRepository metaModel = Beans.get(MetaModelRepository.class);
		 MetaModel m = metaModel.findByName("Party");
		 SequenceRepository seqRepo = Beans.get(SequenceRepository.class);
		 Sequence seq = seqRepo.all().filter("self.model = ?", m).fetchOne();
		 
		  String sequence = "";
		   
		  String prefix = seq.getPrefix();
		  String suffix = seq.getSuffix();
		  Integer padding = seq.getPadding();
		  Integer nextNumber = Integer.parseInt(seq.getNextNumber());
		  
		  //if(party)
		  String num = String.format("%0" + padding + "d", nextNumber);
		  if(!StringUtils.isBlank(suffix)) {
			   sequence = prefix+num+suffix;
		  }
		  else {
			     sequence = prefix+num;
		  }
		  
		   nextNumber = nextNumber+1;
	       System.out.println(nextNumber);
	       seq.setNextNumber(nextNumber.toString());
	       seqRepo.save(seq);
		  
		 // String p = "0";
		  
	     /*for(int i = 0;i<padding-2;i++) {
			       p = p + "0";
		   }*/
	  /*     System.out.println(p);
		 int s = 0;
		 String str = String.valueOf(s);  */
	      
	//	   System.out.println(str);
		  
		  // System.out.println(String.format("%0" + padding + "d", nextNumber));
	        // sequence = prefix+nextNumber.toString()+suffix;
	       
		 return sequence;
		
	}
   }
