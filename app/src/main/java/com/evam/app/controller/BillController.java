package com.evam.app.controller;

import com.evam.app.model.Bill;
import com.evam.app.model.Customer;
import com.evam.app.service.CustomerService;
import com.evam.app.shared.CustomResponse;
import com.evam.app.service.BillService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/v1/bills")
public class BillController {

    CustomerService customerService;
    BillService billService;

    public BillController(CustomerService customerService, BillService billService) {
        this.customerService = customerService;
        this.billService = billService;
    }

    // all bills for all Customers, includes paid and unpaid bills
    @GetMapping("/getAllBills")
    ResponseEntity<?> getAllBills(){
        List<Bill> allBills = billService.findAll();

        return CustomResponse.getOkResponseEntity(allBills,"Request successful.");
    }

    // create a bill
    @PostMapping("/createBill")
    ResponseEntity<?> createBill(@Valid @RequestBody Bill bill, Throwable throwable){
        try{
            Bill billInDb = billService.save(bill); //bill in database
            if (billInDb==null){
                return CustomResponse.getBadResponseEntity("Subscriber not found .");
            }
            return CustomResponse.getOkResponseEntity(billInDb, "Bill created.");
        }
        catch (HttpMessageNotReadableException e){
            e.printStackTrace();
            return CustomResponse.getBadResponseEntity(
                    "You entered the wrong value in the variables. Check the entered information.");
        }
        catch (Exception e){
            e.printStackTrace();
            return CustomResponse.getBadResponseEntity(e.getMessage());
        }
    }

    // Find Customer's bill with Customer id
    @GetMapping("/getBillsWithCustomerId/{id}")
    ResponseEntity<?> getBillsOfCustomerWithId(@PathVariable UUID id){

        Customer customerInDb = customerService.findById(id);
        return getResponseForBillsOfCustomer(customerInDb);
    }


    // Find bill with bill id
    @GetMapping("/getBillWithId/{billId}")
    ResponseEntity<?> findBillWithId(@PathVariable int billId){
        Optional<Bill> bill = billService.findById(billId);
        if(bill.isPresent()){
            if(bill.get().isPaid()){
                return CustomResponse.getOkResponseEntity(bill.get(),"Bill paid before.");
            }else{
                return CustomResponse.getOkResponseEntity(bill.get(),"Bill not paid.");
            }
        }

        return CustomResponse.getBadResponseEntity("Bill not found.");
    }

    // Delete bill with bill ID.
    @DeleteMapping("/deleteBill/{billId}")
    ResponseEntity<?> removeBill(@PathVariable int billId){
        Optional<Bill> bill = billService.findById(billId);
        if(bill.isPresent()) {
            try {
                billService.deleteBill(bill.get());
                return CustomResponse.getOkResponseEntity(null,"Bill deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                return CustomResponse.getBadResponseEntity(e.getMessage());
            }
        }
        return CustomResponse.getBadResponseEntity("Invalid bill id.");
    }


    // find customer's bills. paid or unpaid or exist .
    private ResponseEntity<?> getResponseForBillsOfCustomer(Customer customerInDb) {

        List<Bill> billsOfCustomer ; // all bills of Customer
        List<Bill> notPaidBills = new ArrayList<>(); // not paid bills for Customer

        if (customerInDb != null) { // is Customer available in database

            billsOfCustomer = customerInDb.getBills(); // if Customer exist get bill list

            if (CollectionUtils.isEmpty(billsOfCustomer)) { // Customer doesn't have any bill.
                return CustomResponse.getOkResponseEntity(null,"Customer does not have any bill");
            }
            else{   // if Customer has debt get unpaid bill list.
                for (Bill bill:billsOfCustomer) {
                    if(!bill.isPaid()){
                        notPaidBills.add(bill);
                    }
                }   // is unpaid bill list empty or Customer has any debt ?
                if (CollectionUtils.isEmpty(notPaidBills))
                    return CustomResponse.getOkResponseEntity(null,"The Customer has no debt and unpaid bill.");
                else
                    return CustomResponse.getOkResponseEntity(notPaidBills,"The Customer's list of unpaid bills.");
            }

        } else      //Customer does not exist in database.
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST, "Customer does not exist."),
                    HttpStatus.BAD_REQUEST);
    }

}
