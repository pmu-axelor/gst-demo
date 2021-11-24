package com.axelor.gst.service;

import java.math.BigDecimal;
import java.util.List;

import com.axelor.common.StringUtils;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class InvoiceServiceImpl implements InvoiceService {

	protected MetaModelRepository metaModelRepository;
	protected SequenceRepository sequenceRepository;

	@Inject
	public InvoiceServiceImpl(MetaModelRepository metaModelRepository, SequenceRepository sequenceRepository) {

		this.metaModelRepository = metaModelRepository;
		this.sequenceRepository = sequenceRepository;

	}

	@Override
	public void computeInvoices(Invoice invoice) {

		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal netIgst = BigDecimal.ZERO;
		BigDecimal netSgst = BigDecimal.ZERO;
		BigDecimal netCsgt = BigDecimal.ZERO;
		BigDecimal grossAmount = BigDecimal.ZERO;

		List<InvoiceLine> invoiceLine = invoice.getInvoiceitemList();

		for (InvoiceLine invcLine : invoiceLine) {
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

		MetaModel metaModel = metaModelRepository.findByName("Invoice");
		Sequence seq = sequenceRepository.all().filter("self.model = ? ", metaModel).fetchOne();

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
