package com.coderlong.service;

import com.coderlong.dao.CustomerDao;
import com.coderlong.domain.Customer;

public interface CustomerService {
	Customer findOne(String cusId);
	
}
