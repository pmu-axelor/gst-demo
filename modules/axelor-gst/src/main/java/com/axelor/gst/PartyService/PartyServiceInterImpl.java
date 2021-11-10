package com.axelor.gst.PartyService;

import java.util.Locale;

import com.axelor.gst.db.Sequence;
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
		  int padding = seq.getPadding();
		//  System.out.println(padding);
		  Integer nextNumber = Integer.parseInt(seq.getNextNumber());
		  String p = "0";
		  
	     for(int i = 0;i<padding-2;i++) {
			       p = p + "0";
		   }
	     //  System.out.println(p);
		/* int s = 0;
		 String str = String.valueOf(s);
	     str = String.format("%0" + str.length() + "d", Long.parseLong(str) + 1);
		   System.out.println(str);*/
	     /*  String s = "";
	      for (int i = 0; i < padding-1; i++) {
	          s = String.format(Locale.getDefault(), "%04d", i);  
	          
	      }  System.out.println(s);*/
	       
	      sequence = prefix+p+nextNumber.toString()+suffix;
		  
		   nextNumber = nextNumber+1;
		   System.out.println(nextNumber);
		   seq.setNextNumber(nextNumber.toString());
		   seqRepo.save(seq);
		  
		// System.out.println(sequence);
		   
		/*  {"padding":4,"updatedBy":{"code":"admin","name":"Administrator","id":1,"$version":0},
			  "prefix":"ABC","updatedOn":"2021-11-09T09:51:35.860Z",
			  "suffix":"AXE","createdOn":"2021-11-08T07:40:30.721Z",
			  "attrs":null,"nextNumber":"1",
			  "createdBy":{"code":"admin","name":"Administrator","id":1,"$version":0},
			  "model":{"name":"Party","id":50,"$version":0},
			  "id":1,"selected":false,"$version":2}*/
	    
		return sequence;
		
	}
    
	
	

}
