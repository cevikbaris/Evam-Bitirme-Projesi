package com.evam.app.service;

import com.evam.app.model.Bill;
import com.evam.app.model.Customer;
import com.evam.app.repository.BillRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BillService {

    BillRepository billRepository;
    CustomerService customerService;

    public BillService(BillRepository billRepository , CustomerService customerService) {
        this.billRepository = billRepository;
        this.customerService = customerService;
    }

    public Bill save(Bill bill) { // find the Customer who has that bill with subscriber no.
        Customer customer = customerService.findById(bill.getSubscriberNo());
        if(customer !=null){
            customer.getBills().add(bill);
            bill.setProcessDate(new Date());
            bill.setCustomer(customer);
            return billRepository.save(bill);
        }
        else    // subscriber number is wrong . customer not found.
            return null;
    }

    //find all bills
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    //find bill by id and return optional bill.
    public Optional<Bill> findById(int billId) {
        return billRepository.findById(billId);
    }

    // find bill by subscriber number.
    public Bill findBySubscriberNo(UUID subscriberNo) {
        return billRepository.findBySubscriberNo(subscriberNo);
    }

    // delete bill with bill entity.
    public void deleteBill(Bill bill) {
        billRepository.delete(bill);
    }
}
