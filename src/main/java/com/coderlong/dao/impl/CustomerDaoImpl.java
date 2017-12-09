package com.coderlong.dao.impl;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.coderlong.dao.CustomerDao;
import com.coderlong.domain.Customer;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

	@Override
	public Customer findOne(String cusId) {
		return this.getHibernateTemplate().get(Customer.class,cusId);
	}
	
}
