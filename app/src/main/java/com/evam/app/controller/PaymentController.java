package com.evam.app.controller;

import com.evam.app.model.Bill;
import com.evam.app.model.Customer;
import com.evam.app.model.Payment;
import com.evam.app.service.BillService;
import com.evam.app.service.PaymentService;
import com.evam.app.service.CustomerService;
import com.evam.app.shared.CustomResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    CustomerService customerService;
    BillService billService;
    PaymentService paymentService;

    public PaymentController(CustomerService customerService, BillService billService, PaymentService paymentService) {
        this.customerService = customerService;
        this.billService = billService;
        this.paymentService = paymentService;
    }

    @PostMapping("/payBills/{subscriberNo}")
    ResponseEntity<?> createPayment(@PathVariable UUID subscriberNo){ //subscriber number

        // if there is unpaid bill this will be true.
        boolean unpaidBillExist = false;

         //  Find customer with id.
        Customer customerInDb = customerService.findById(subscriberNo);

        if(customerInDb==null)
            return CustomResponse.getBadResponseEntity("Invalid Subscriber number.");

        // Check does customer have payment. If not create new payment, else get current payment .
        Payment payment = customerInDb.getPayment() == null ? new Payment() : customerInDb.getPayment() ;

        // If there is no payment before, create new big decimal as 0.00 or
        //  get current  total paid amount.
        BigDecimal totalAmount =
                customerInDb.getPayment() == null
                ? new BigDecimal("0.00")
                : customerInDb.getPayment().getTotalAmount();


        // all bills for customer.
        List<Bill> billsForPayment = customerInDb.getBills();

        if(!CollectionUtils.isEmpty(billsForPayment)){
            for(Bill bill : billsForPayment){

                // for each unpaid bill, update as paid true and sum the total amount for bill prices.
                if (!bill.isPaid()) {
                    totalAmount =totalAmount.add(bill.getBillPrice());
                    // Customer paid this bill, payment is successful
                    bill.setPaid(true);
                    unpaidBillExist=true;
                }
            }
            // if there is unpaid bill, pay that.
            if (unpaidBillExist) {
                payment.setPaymentDate(new Date());
                payment.setTotalAmount(totalAmount); // price which will pay
                payment.setSubscriberNo(subscriberNo);
                payment.setCustomer(customerInDb);


                try {   // save payment and get saved payment
                    Payment savedPayment = paymentService.save(payment);
                    customerInDb.setPayment(savedPayment); // bill has payment now. Because it is paid.
                    customerService.save(customerInDb); // add payment to customer.
                    return CustomResponse.getOkResponseEntity(savedPayment,
                            "Payment successful.");
                } catch (Exception e) {
                    e.printStackTrace();
                    return CustomResponse.getBadResponseEntity(e.getMessage());
                }
            } else {
                return CustomResponse.getOkResponseEntity(null, "There is no unpaid bill.");
            }

        }
        else
            return CustomResponse.getBadResponseEntity("There is no bill.");
    }
}
