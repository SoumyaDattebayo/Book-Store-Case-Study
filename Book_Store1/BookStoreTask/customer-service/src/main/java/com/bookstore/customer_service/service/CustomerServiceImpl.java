package com.bookstore.customer_service.service;

import com.bookstore.customer_service.dataModel.Customer;
import com.bookstore.customer_service.exceptions.CustomerNotFoundException;
import com.bookstore.customer_service.exceptions.DuplicateCustomerFoundException;
import com.bookstore.customer_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{


    private final CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
    }

    public Customer createCustomer(Customer customer) {
        if(customerRepository.findByEmail(customer.getEmail()) != null){
            throw new DuplicateCustomerFoundException("Customer with email: "+customer.getEmail()+" Already Exist");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));

        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
        customerRepository.delete(customer);
    }
}