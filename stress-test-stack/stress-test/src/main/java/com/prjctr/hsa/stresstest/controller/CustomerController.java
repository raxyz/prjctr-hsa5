package com.prjctr.hsa.stresstest.controller;

import java.util.List;

import com.prjctr.hsa.stresstest.entity.Customer;
import com.prjctr.hsa.stresstest.entity.dto.CustomerDto;
import com.prjctr.hsa.stresstest.service.CustomerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private CustomerService service;

    @GetMapping
    public List<Customer> getAllUsers() throws InterruptedException {
        return service.getCustomers();
    }

    @PostMapping
    public Customer creatCustomer(@RequestBody CustomerDto customerDto) {
        return service.createCustomer(customerDto);
    }

    @GetMapping("/clear")
    public String resetCache() {
        return service.clearCache();
    }
}
