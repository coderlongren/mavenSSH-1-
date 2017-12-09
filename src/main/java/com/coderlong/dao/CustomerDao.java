package com.coderlong.dao;

import com.coderlong.domain.Customer;

public interface CustomerDao {
	public Customer findOne(String cusId);
}
