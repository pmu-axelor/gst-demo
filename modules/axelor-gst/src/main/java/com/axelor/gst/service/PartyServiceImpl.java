package com.axelor.gst.service;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class PartyServiceImpl implements PartyService {

	protected MetaModelRepository metaModelRepository;
	protected SequenceRepository sequenceRepository;

	@Inject
	public PartyServiceImpl(MetaModelRepository metaModelRepository, SequenceRepository sequenceRepository) {

		this.metaModelRepository = metaModelRepository;
		this.sequenceRepository = sequenceRepository;

	}

	@Override
	@Transactional
	public String setSequence() throws Exception {

		MetaModel metaModel = metaModelRepository.findByName("Party");
		Sequence seq = sequenceRepository.all().filter("self.model = ?", metaModel).fetchOne();

		String sequence = "";
		String prefix = seq.getPrefix();
		String suffix = seq.getSuffix();
		Integer padding = seq.getPadding();

		if (seq.getNextNumber() == null) {
			seq.setNextNumber("1");
			Integer nextNumber = Integer.parseInt(seq.getNextNumber());
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

		else if (seq.getNextNumber() != null) {
			Integer nextNumber = Integer.parseInt(seq.getNextNumber());
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
