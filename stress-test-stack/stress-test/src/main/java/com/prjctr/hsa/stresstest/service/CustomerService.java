package com.prjctr.hsa.stresstest.service;

import java.util.List;

import com.prjctr.hsa.stresstest.entity.Customer;
import com.prjctr.hsa.stresstest.entity.dto.CustomerDto;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto);
    List<Customer> getCustomers();
    String clearCache();
}
