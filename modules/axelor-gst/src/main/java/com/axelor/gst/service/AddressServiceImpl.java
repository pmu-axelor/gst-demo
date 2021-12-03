package com.axelor.gst.service;

import java.util.List;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.repo.AddressRepository;

public class AddressServiceImpl implements AddressService {

	@Override
	public void getInvoiceAddresses(Invoice invoice) {

		if (invoice.getParty() != null) {
			invoice.setPartyContact(getPrimaryContact(invoice));
			invoice.setInvoiceAddress(getInvoiceAddress(invoice));

		}
	}

	@Override
	public void getShippingAddresses(Invoice invoice) {
		if (invoice.getParty() != null) {
			invoice.setShippingAddress(getShippingAddress(invoice));

		}

	}

	protected Address getInvoiceAddress(Invoice invoice) {
		List<Address> addresses = invoice.getParty().getAddressList();

		for (Address address : addresses) {
			String type = address.getType();

			switch (type) {

			case AddressRepository.ADDRESS_DEFAULT:
				return address;

			case AddressRepository.ADDRESS_INVOICE:
				return address;

			default:
				return null;

			}
		}
		return null;

	}

	protected Address getShippingAddress(Invoice invoice) {
		List<Address> addresses = invoice.getParty().getAddressList();

		for (Address address : addresses) {
			String type = address.getType();

			switch (type) {

			case AddressRepository.ADDRESS_DEFAULT:
				return address;

			case AddressRepository.ADDRESS_SHIPPING:
				return address;

			default:
				return null;

			}
		}
		return null;

	}

	protected Contact getPrimaryContact(Invoice invoice) {

		List<Contact> contacts = invoice.getParty().getContactList();

		for (Contact c : contacts) {
			if ("primary".equals(c.getType())) {
				return c;
			}
		}
		return null;

	}

}
