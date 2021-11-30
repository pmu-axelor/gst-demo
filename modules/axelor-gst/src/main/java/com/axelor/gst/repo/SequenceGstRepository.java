package com.axelor.gst.repo;


import com.axelor.gst.db.repo.SequenceRepository;

public class SequenceGstRepository extends SequenceRepository {

	/*
	 * @Override public Sequence save(Sequence sequence) {
	 * 
	 * int count = 0; String prefix = sequence.getPrefix(); String suffix =
	 * sequence.getSuffix(); Integer padding = sequence.getPadding(); // Integer
	 * nextNumber = Integer.parseInt(seq.getNextNumber()); String num =
	 * String.format("%0" + padding + "d", count);
	 * 
	 * if (!StringUtils.isBlank(sequence.getSuffix())) { sequence =
	 * sequence.getPrefix() + num + sequence.getSuffix(); } else { sequence =
	 * sequence.getPrefix() + num; }
	 * 
	 * 
	 * 
	 * 
	 * return super.save(sequence); }
	 */

}
