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

	/*
	 * protected Address getPartyAddress(Invoice invoice, String addressType) {
	 * List<Address> addresses = invoice.getParty().getAddressList(); for (Address
	 * address : addresses) {
	 * 
	 * 
	 * 
	 * if(addressType.equals(AddressRepository.ADDRESS_DEFAULT)) { return address; }
	 * 
	 * else if(addressType.equals(AddressRepository.ADDRESS_INVOICE)) { return
	 * address; }
	 * 
	 * else if(addressType.equals(AddressRepository.ADDRESS_SHIPPING)) { return
	 * address; }
	 * 
	 * else { return null; }
	 * 
	 * 
	 * }
	 * 
	 * return null;
	 * 
	 * }
	 */

	protected Address getInvoiceAddress(Invoice invoice) {
		List<Address> addresses = invoice.getParty().getAddressList();

		for (Address address : addresses) {
			String type = address.getType();

			switch (type) {
			case "default":
				return address;

			case "invoice":
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
			case "default":
				return address;

			case "shipping":
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
