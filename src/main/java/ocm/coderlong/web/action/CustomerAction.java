package ocm.coderlong.web.action;

import com.coderlong.domain.Customer;
import com.coderlong.service.CustomerService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CustomerAction extends ActionSupport {
	private CustomerService customerService;
	private String cusId;
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public void setCusId(String cusId) {
		this.cusId = cusId;
	}
	// 根据主键查询
	public String findOne() throws Exception{
		Customer customer = customerService.findOne(cusId);
		ActionContext.getContext().getValueStack().push(customer);
		
		return SUCCESS;
	}
	
	
	
}
