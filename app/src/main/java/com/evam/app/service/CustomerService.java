package com.evam.app.service;

import com.evam.app.model.Customer;
import com.evam.app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
       return customerRepository.save(customer); //return Customer information
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    // find customer by UUID. If no exist return null.
    public Customer findById(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
}
