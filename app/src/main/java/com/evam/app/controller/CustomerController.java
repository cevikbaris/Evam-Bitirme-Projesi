package com.evam.app.controller;

import com.evam.app.model.Bill;
import com.evam.app.model.Customer;
import com.evam.app.model.Payment;
import com.evam.app.service.BillService;
import com.evam.app.service.PaymentService;
import com.evam.app.shared.CustomResponse;
import com.evam.app.service.CustomerService;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    BillService billService;
    PaymentService paymentService;
    CustomerService customerService;

    public CustomerController(BillService billService, PaymentService paymentService, CustomerService customerService) {
        this.billService = billService;
        this.paymentService = paymentService;
        this.customerService = customerService;
    }

    @GetMapping("/getAllCustomers")
    ResponseEntity<?> getCustomers(HttpServletRequest request){
        List<Customer> customerList =  customerService.getCustomers();
        if(!CollectionUtils.isEmpty(customerList))
            return CustomResponse.getOkResponseEntity(customerList,"All Customers.",request.getRequestURI());
        else
            return CustomResponse.getOkResponseEntity(null,"There are no Customers",request.getRequestURI());
    }


    @GetMapping("/getCustomerById/{id}")
    ResponseEntity<?> getCustomerBySubscriberNo(@PathVariable UUID id){

        Customer customer = customerService.findById(id);

        // If customer exist use first message, else use the other message.
        return CustomResponse.getResponseIfObjectNotNull(customer,
                "Request successful. Customer found with uuid",
                "Wrong id number. Customer can not found.");
    }

    // Create customer with request body. It must be valid.
    @PostMapping("/createCustomer")
    ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, HttpServletRequest request){
        try{
           return CustomResponse.getOkResponseEntity(customerService.save(customer), "Customer created.",
                   request.getRequestURI());
       }
       catch (Exception e){
           e.printStackTrace();
           return CustomResponse.getBadResponseEntity(e.getMessage());
       }

    }

    @PutMapping("/updateCustomer")
    ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer){
       Customer customerInDb=null ;
       // If id exists, find it with id and update subscriber number, name, surname,
       // otherwise find it with subscriber number, update name and surname.
       if (customer.getId()!=null){
           customerInDb = customerService.findById(customer.getId());
       }
        return updateCustomerReturnResponseEntity(customer, customerInDb);
    }

    // Delete customer if ID true.
    @DeleteMapping("/deleteCustomer/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable UUID id){
        Customer customer =  customerService.findById(id);
        if(customer!=null)
            try{
                customerService.deleteCustomer(customer);
                return CustomResponse.getOkResponseEntity(null,"Customer deleted.");
            }catch (Exception e){
                e.printStackTrace();
                return CustomResponse.getBadResponseEntity(e.getMessage());
            }
        return CustomResponse.getBadResponseEntity("Customer not found.");
    }

    // Check valid customer and given customer. Update customer which in database
    private ResponseEntity<CustomResponse> updateCustomerReturnResponseEntity(Customer customer, Customer customerInDb) {
        if(customerInDb != null){

            //change customer's information who is in database.
            customerInDb.setName(customer.getName());
            customerInDb.setSurname(customer.getSurname());
            // if there is a customer id update  name - surname
             //try update customer and save it.
            try{
                // URL path may add manually or with HttpServletRequest. It is example for manual adding.
                return CustomResponse.getOkResponseEntity(customerService.save(customerInDb),"Customer updated."
                ,"/v1/customers/updateCustomer");

            }catch (Exception e){
                e.printStackTrace();

                return CustomResponse.getBadResponseEntity(e.getMessage());
            }
        }else{
            return CustomResponse.getBadResponseEntity("Customer not found.");
        }
    }

}
