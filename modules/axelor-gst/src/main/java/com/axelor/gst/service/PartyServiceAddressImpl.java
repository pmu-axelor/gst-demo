package com.axelor.gst.service;

import java.util.List;

import javax.validation.ValidationException;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Party;

public class PartyServiceAddressImpl {

	public void validate(Party party) {

		List<Address> addresses = party.getAddressList();
		for (Address address : addresses) {
			if (!"invoice".equals(address.getType())) {
				throw new ValidationException("address type is not invoice");
			}
		}
	}

}
