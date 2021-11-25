package com.axelor.gst.service;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;

import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class PartyServiceImpl implements PartyService {

	protected MetaModelRepository metaModelRepository;
	protected SequenceRepository sequenceRepository;

	@Inject
	public PartyServiceImpl(SequenceRepository sequenceRepository) {

		this.sequenceRepository = sequenceRepository;

	}

	@Override
	@Transactional
	public String setSequence() throws Exception {

		Sequence seq = sequenceRepository.all().filter("self.model.name = ?", Party.class.getSimpleName()).fetchOne();

		String sequence = "";
		String prefix = seq.getPrefix();
		String suffix = seq.getSuffix();
		Integer padding = seq.getPadding();
		String next = seq.getNextNumber();

		if (next == null) {
			seq.setNextNumber("1");
			Integer nextNumber = Integer.parseInt(next);
			String num = String.format("%0" + padding + "d", nextNumber);

			if (!StringUtils.isBlank(suffix)) {
				sequence = prefix + num + suffix;
			} else {
				sequence = prefix + num;
			}

			nextNumber = nextNumber + 1;
			seq.setNextNumber(nextNumber.toString());
			sequenceRepository.save(seq);
		}

		else if (next != null) {
			Integer nextNumber = Integer.parseInt(next);
			String num = String.format("%0" + padding + "d", nextNumber);
			if (!StringUtils.isBlank(suffix)) {
				sequence = prefix + num + suffix;
			} else {
				sequence = prefix + num;
			}

			nextNumber = nextNumber + 1;
			seq.setNextNumber(nextNumber.toString());
			sequenceRepository.save(seq);
		}

		return sequence;
	}

}
