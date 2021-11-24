package com.axelor.gst.service;

import java.time.LocalDateTime;
import java.util.List;

import com.axelor.gst.db.Company;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.repo.CompanyRepository;
import com.axelor.gst.db.repo.InvoiceRepository;
import com.axelor.gst.db.repo.PartyRepository;
import com.axelor.gst.db.repo.ProductRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class ProductServiceImpl implements ProductService {

	protected ProductRepository productRepository;
	protected CompanyRepository companyRepository;
	protected PartyRepository partyRepository;
	protected InvoiceRepository invoiceRepository;
	protected AddressServiceImpl addressService;
	protected InvoiceLineService invoiceLineService;
	protected InvoiceService invoiceService;

	@Inject
	public ProductServiceImpl(ProductRepository productRepository, CompanyRepository companyRepository,
			PartyRepository partyRepository, InvoiceRepository invoiceRepository, AddressServiceImpl addressService,
			InvoiceLineService invoiceLineService, InvoiceService invoiceService) {

		this.productRepository = productRepository;
		this.companyRepository = companyRepository;
		this.partyRepository = partyRepository;
		this.invoiceRepository = invoiceRepository;
		this.addressService = addressService;
		this.invoiceLineService = invoiceLineService;
		this.invoiceService = invoiceService;

	}

	@Override
	@Transactional
	public Invoice createInvoice(List<Integer> ids) {

		Company company = companyRepository.all().fetchOne();
		Party party = partyRepository.all().fetchOne();
		Invoice invoice = new Invoice();
		Product product = new Product();

		invoice.setStatus("draft");
		invoice.setDate(LocalDateTime.now());
		invoice.setCompany(company);
		invoice.setParty(party);
		addressService.getInvoiceAddresses(invoice);
		addressService.getShippingAddresses(invoice);

		for (Integer l : ids) {
			product = productRepository.find(Long.valueOf(l));
			InvoiceLine invcLine = new InvoiceLine();
			invcLine.setProduct(product);
			invcLine.setHsbn(product.getHsbn());
			invcLine.setItem(product.getCode() + "-" + product.getName());
			invcLine.setGstRate(product.getGstrate());
			invcLine.setPrice(product.getSaleprice());
			invcLine.setQty(1);
			invoiceLineService.computeInvoiceLinesItems(invoice, invcLine);
			invoice.addInvoiceitemListItem(invcLine);;
			invoiceService.computeInvoices(invoice);

		}

		return invoiceRepository.save(invoice);

	}

}
