package com.coderlong.service.impl;

import com.coderlong.dao.CustomerDao;
import com.coderlong.domain.Customer;
import com.coderlong.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	private CustomerDao customerDao;
	
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Customer findOne(String cusId) {
		// TODO Auto-generated method stub
		return customerDao.findOne(cusId);
	}
	
}
