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
	public Invoice createInvoice(List<Long> ids) {

		Company company = companyRepository.all().fetchOne();
		Party party = partyRepository.all().fetchOne();
		Invoice invoice = new Invoice();

		invoice.setStatus(InvoiceRepository.STATUS_DRAFT);
		invoice.setDate(LocalDateTime.now());
		invoice.setCompany(company);
		invoice.setParty(party);
		addressService.getInvoiceAddresses(invoice);
		addressService.getShippingAddresses(invoice);
		List<Product> productList = productRepository.all().filter("self.id in ?", ids).fetch();
		for (Product product : productList) {
			InvoiceLine invoiceLine = new InvoiceLine();
			invoiceLine.setProduct(product);
			invoiceLine.setHsbn(product.getHsbn());
			invoiceLine.setItem(product.getCode() + "-" + product.getName());
			invoiceLine.setGstRate(product.getGstRate());
			invoiceLine.setPrice(product.getSalePrice());
			invoiceLine.setQty(1);
			try {
				invoiceLineService.computeInvoiceLinesItems(invoice, invoiceLine);
			} catch (Exception e) {
				e.printStackTrace();
			}
			invoice.addInvoiceitemListItem(invoiceLine);
			invoiceService.computeInvoices(invoice);

		}

		return invoiceRepository.save(invoice);

	}

}
