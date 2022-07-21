# Spring Project Report

### Customer Response and Request JSON examples.
------------
&nbsp;

- **POST Request**. Path: http://localhost:8080/v1/customers/createCustomer -> Create a new customer

```json
{
    "name": "Barış",
    "surname": "Çevik"
}
```


- **Response**
```json
{
    "timestamp": "21-07-2022 08:33:54",
    "code": 200,
    "status": "OK",
    "message": "Customer created.",
    "data": {
        "id": "e0db5b6e-422a-41f8-95ba-0e0159d1086d",
        "name": "Barış",
        "surname": "Çevik",
        "bills": null,
        "payment": null
    },
    "path": "/v1/customers/createCustomer"
}
```
&nbsp;
- **POST Request** with wrong values. Path: http://localhost:8080/v1/customers/createCustomer
```json
{
    "name": "Ba",
    "surname": null
}
```
- **Response**
```json
{
    "message": "Validation failed for object = customer",
    "timestamp": "2022-07-21",
    "status": 400,
    "path": "/v1/customers/createCustomer",
    "errors": [
        "Name must be minimum 3 character.",
        "Surname can not be null."
    ]
}
```
&nbsp;
- **UPDATE Request**. Path: http://localhost:8080/v1/customers/updateCustomer -> Update Customer. Find customer by UUID and change the given name and surname. It can update  name, surname or both.
```json
{
    "id":"b8f54892-59f7-4539-8cf2-f552783d2a9e",
    "name":"Ahmet",
    "surname":"Demir"
}
```
- **Response**
```json
{
    "timestamp": "21-07-2022 08:44:03",
    "code": 200,
    "status": "OK",
    "message": "Customer updated.",
    "data": {
        "id": "b8f54892-59f7-4539-8cf2-f552783d2a9e",
        "name": "Ahmet",
        "surname": "Demir",
        "bills": [],
        "payment": null
    },
    "path": "/v1/customers/updateCustomer"

}
```
&nbsp;
- **UPDATE Request**. Wrong UUID. Path: http://localhost:8080/v1/customers/updateCustomer 
```json
{
    "id":"b8f54892-59f7-4539-8cf2-f552783d2a8e",
    "name":"Ahmet",
    "surname":"Demir"
}
```
- **Response**
```json
{
    "timestamp": "21-07-2022 08:47:58",
    "code": 400,
    "status": "BAD_REQUEST",
    "message": "Customer not found."
}

```
&nbsp;
- **UPDATE Request**. Empty Name and Surname. Path: http://localhost:8080/v1/customers/updateCustomer  
```json
{
    "id":"b8f54892-59f7-4539-8cf2-f552783d2a9e",
    "name":"",
    "surname":""
}
```
- **Response**
```json
{
    "message": "Validation failed for object = customer",
    "timestamp": "2022-07-21",
    "status": 400,
    "path": "/v1/customers/updateCustomer",
    "errors": [
        "Name must be minimum 3 character.",
        "Surname must be minimum 3 character."
    ]
}
```
&nbsp;
- **UPDATE Request**. Wrong UUID format. Path: http://localhost:8080/v1/customers/updateCustomer 

```json
{
    "id":"b8f54892a9e",
    "name":"Ahmet",
    "surname":"Demir"
}
```
- **Response**

```json
{
    "timestamp": "2022-07-21",
    "status": 400,
    "path": "/v1/customers/updateCustomer",
    "message": "Malformed JSON request"
}
```
&nbsp;
- **GET Request** Path: http://localhost:8080/v1/customers/getAllCustomers -> Get all customers. After bill added to the customer and some bills paid.

>**http://localhost:8080/v1/customers/getAllCustomers**

- **Response**

```json
{
    "timestamp": "21-07-2022 10:02:08",
    "code": 200,
    "status": "OK",
    "message": "All Customers.",
    "data": [
        {
            "id": "b538f97d-3113-46c2-868e-333ee4560610",
            "name": "Barış",
            "surname": "Çevik",
            "bills": [
                {
                    "billId": 1,
                    "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610",
                    "billPrice": 300.00,
                    "processDate": "2022-07-21T19:01:21.540+00:00",
                    "paid": true
                },
                {
                    "billId": 2,
                    "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610",
                    "billPrice": 100.55,
                    "processDate": "2022-07-21T21:00:08.153+00:00",
                    "paid": true
                }
            ],
            "payment": {
                "paymentId": 3,
                "totalAmount": 400.55,
                "paymentDate": "2022-07-21T21:48:27.083+00:00",
                "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610"
            }
        },
        {
            "id": "f23f04fa-f72c-42dc-bfa6-97ec38c64377",
            "name": "Ali",
            "surname": "Can",
            "bills": [],
            "payment": null
        },
        {
            "id": "b8f54892-59f7-4539-8cf2-f552783d2a9e",
            "name": "Ahmet",
            "surname": "Demir",
            "bills": [],
            "payment": null
        },
        {
            "id": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
            "name": "Cem",
            "surname": "Demir",
            "bills": [
                {
                    "billId": 4,
                    "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
                    "billPrice": 25.55,
                    "processDate": "2022-07-21T22:02:05.697+00:00",
                    "paid": false
                }
            ],
            "payment": null
        },
        {
            "id": "0504da10-54d8-4b4c-bef5-702ac6100527",
            "name": "Ayşe",
            "surname": "Türk",
            "bills": [],
            "payment": null
        }
    ],
    "path": "/v1/customers/getAllCustomers"
}
```
&nbsp;
- **GET Request**. Path: http://localhost:8080/v1/customers/getCustomerById/{id} -> Get Customer with UUID. Get Customer's bills and payment. Returns paid and unpaid bills and the total of paid bills.

>**http://localhost:8080/v1/customers/getCustomerById/7d85ff1d-2236-4ed8-9e46-0c297afbac5d**

- **Response**

```json
{
    "timestamp": "21-07-2022 10:07:16",
    "code": 200,
    "status": "OK",
    "message": "Request successful. Customer found with uuid",
    "data": {
        "id": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
        "name": "Cem",
        "surname": "Demir",
        "bills": [
            {
                "billId": 4,
                "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
                "billPrice": 25.55,
                "processDate": "2022-07-21T22:02:05.697+00:00",
                "paid": true
            },
            {
                "billId": 5,
                "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
                "billPrice": 70.10,
                "processDate": "2022-07-21T22:06:00.214+00:00",
                "paid": true
            },
            {
                "billId": 8,
                "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
                "billPrice": 150.00,
                "processDate": "2022-07-21T22:07:13.913+00:00",
                "paid": false
            }
        ],
        "payment": {
            "paymentId": 6,
            "totalAmount": 95.65,
            "paymentDate": "2022-07-21T22:06:16.787+00:00",
            "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d"
        }
    }
}
```
&nbsp;
- If there is no Customer belonging to the entered UUID. For the previous request.

```json
{
    "timestamp": "21-07-2022 10:10:36",
    "code": 400,
    "status": "BAD_REQUEST",
    "message": "Wrong id number. Customer can not found."
}
```
&nbsp;
- **DELETE Request** Path: http://localhost:8080/v1/customers/deleteCustomer/{id} -> Delete Customer by UUID.  When the customer is deleted, the customer's payments and invoices are deleted.

>**http://localhost:8080/v1/customers/deleteCustomer/b84f1a5c-73ac-4ffe-9026-6b1d0c66504d**

- **Response**

```json
{
    "timestamp": "21-07-2022 09:30:14",
    "code": 200,
    "status": "OK",
    "message": "Customer deleted."
}
```
- SQL operations for customer deletion in spring boot.

```java
Hibernate: delete from bills where bill_id=?
Hibernate: delete from bills where bill_id=?
Hibernate: delete from payments where payment_id=?
Hibernate: delete from customers where id=?
```
&nbsp;
&nbsp;

### Bill Response and Request JSON examples.
------------

- **POST Request**. Path: http://localhost:8080/v1/bills/createBill -> Create a new Bill with Subscriber number(UUID) and Bill price. 
```json
{
    "subscriberNo":"b538f97d-3113-46c2-868e-333ee4560610",
    "billPrice":"100.55"
}
```
- **Response**
```json
{
    "timestamp": "21-07-2022 09:00:08",
    "code": 200,
    "status": "OK",
    "message": "Bill created.",
    "data": {
        "billId": 2,
        "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610",
        "billPrice": 100.55,
        "processDate": "2022-07-21T21:00:08.153+00:00",
        "paid": false
    }
}
```
&nbsp;
- **POST Request**. Negative Bill price. Path: http://localhost:8080/v1/bills/createBill
```json
{
    "subscriberNo":"b538f97d-3113-46c2-868e-333ee4560610",
    "billPrice":"-300.00"
}
```
- **Response**
```json
{
    "message": "Validation failed for object = bill",
    "timestamp": "2022-07-21",
    "status": 400,
    "path": "/v1/bills/createBill",
    "errors": [
        "Bill price must be greater than 0."
    ]
}
```
&nbsp;
- **POST Request**. When Bill price not BigDecimal Path: http://localhost:8080/v1/bills/createBill
```json
{
    "subscriberNo":"b538f97d-3113-46c2-868e-333ee4560610",
    "billPrice":"asd"
}
```
- **Response**
```json
{
    "timestamp": "2022-07-21",
    "status": 400,
    "path": "/v1/bills/createBill",
    "message": "Malformed JSON request"
}
```
&nbsp;
- **GET Request**. Path: http://localhost:8080/v1/bills/getBillsWithCustomerId/{id} -> Find Customer's bills. If there are bills get list of bills. If there is no bill (if all bills are paid or empty) show message that all bills are paid or show message that there are no bills.


>**http://localhost:8080/v1/bills/getBillsWithCustomerId/b538f97d-3113-46c2-868e-333ee4560610**


- **Response**
```json
{
    "timestamp": "21-07-2022 09:35:19",
    "code": 200,
    "status": "OK",
    "message": "The Customer's list of unpaid bills.",
    "data": [
        {
            "billId": 1,
            "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610",
            "billPrice": 300.00,
            "processDate": "2022-07-21T19:01:21.540+00:00",
            "paid": false
        },
        {
            "billId": 2,
            "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610",
            "billPrice": 100.55,
            "processDate": "2022-07-21T21:00:08.153+00:00",
            "paid": false
        }
    ]
}
```


- **GET Request** Get Customer which does not has any bill. Path:http://localhost:8080/v1/bills/getBillsWithCustomerId/{id}

>**http://localhost:8080/v1/bills/getBillsWithCustomerId/0504da10-54d8-4b4c-bef5-702ac6100527**

- **Response**
```json
{
    "timestamp": "21-07-2022 09:46:21",
    "code": 200,
    "status": "OK",
    "message": "Customer does not have any bill"
}
```

- **GET Request** After all bills paid.  Path:http://localhost:8080/v1/bills/getBillsWithCustomerId/{id}

>**http://localhost:8080/v1/bills/getBillsWithCustomerId/b538f97d-3113-46c2-868e-333ee4560610**

- **Response**

```json
{
    "timestamp": "21-07-2022 09:52:57",
    "code": 200,
    "status": "OK",
    "message": "The Customer has no debt and unpaid bill."
}
```
&nbsp;
- **GET Request** Path: http://localhost:8080/v1/bills/getBillWithId/{billId} -> Find bill with bill ID. Show whether the bill has been paid or not. 
 - For unpaid bill.
>**http://localhost:8080/v1/bills/getBillWithId/8** 
- **Response**
```json
{
    "timestamp": "21-07-2022 10:31:10",
    "code": 200,
    "status": "OK",
    "message": "Bill not paid.",
    "data": {
        "billId": 8,
        "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
        "billPrice": 150.00,
        "processDate": "2022-07-21T22:07:13.913+00:00",
        "paid": false
    }
}
```
 - For paid bill.
>**http://localhost:8080/v1/bills/getBillWithId/5**
- **Response**
```json
{
    "timestamp": "21-07-2022 10:32:28",
    "code": 200,
    "status": "OK",
    "message": "Bill paid before.",
    "data": {
        "billId": 5,
        "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
        "billPrice": 70.10,
        "processDate": "2022-07-21T22:06:00.214+00:00",
        "paid": true
    }
}
```
 - When bill does not exist.
>**http://localhost:8080/v1/bills/getBillWithId/7**
- **Response**
```json
{
    "timestamp": "21-07-2022 10:32:08",
    "code": 400,
    "status": "BAD_REQUEST",
    "message": "Bill not found."
}
```
&nbsp;
- **GET Request** Path: http://localhost:8080/v1/bills/getAllBills -> Show all bills. All invoices in the database, independent of the customer.

>**http://localhost:8080/v1/bills/getAllBills**

```json
{
    "timestamp": "21-07-2022 10:31:02",
    "code": 200,
    "status": "OK",
    "message": "Request successful.",
    "data": [
        {
            "billId": 1,
            "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610",
            "billPrice": 300.00,
            "processDate": "2022-07-21T19:01:21.540+00:00",
            "paid": true
        },
        {
            "billId": 2,
            "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610",
            "billPrice": 100.55,
            "processDate": "2022-07-21T21:00:08.153+00:00",
            "paid": true
        },
        {
            "billId": 4,
            "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
            "billPrice": 25.55,
            "processDate": "2022-07-21T22:02:05.697+00:00",
            "paid": true
        },
        {
            "billId": 5,
            "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
            "billPrice": 70.10,
            "processDate": "2022-07-21T22:06:00.214+00:00",
            "paid": true
        },
        {
            "billId": 8,
            "subscriberNo": "7d85ff1d-2236-4ed8-9e46-0c297afbac5d",
            "billPrice": 150.00,
            "processDate": "2022-07-21T22:07:13.913+00:00",
            "paid": false
        }
    ]
}
```
&nbsp;
- **DELETE Request** Path: http://localhost:8080/v1/bills/deleteBill/{billId} -> Delete the bill belonging to the bill ID. 

>**http://localhost:8080/v1/bills/deleteBill/7**

- **Response**

```json
{
    "timestamp": "21-07-2022 10:06:33",
    "code": 200,
    "status": "OK",
    "message": "Bill deleted."
}
```
&nbsp;
&nbsp;
### Payment Request and Response JSON examples
------------

- **GET Request**. Path: http://localhost:8080/v1/payments/payBills/{subscriberNo} -> Pay all bills of Customer. Find Customer with subscriber no (UUID). Return the total price of all paid invoices.

>http://localhost:8080/v1/payments/payBills/b538f97d-3113-46c2-868e-333ee4560610

- **Response**

```json
{
    "timestamp": "21-07-2022 09:48:27",
    "code": 200,
    "status": "OK",
    "message": "Payment successful.",
    "data": {
        "paymentId": 3,
        "totalAmount": 400.55,
        "paymentDate": "2022-07-21T21:48:27.083+00:00",
        "subscriberNo": "b538f97d-3113-46c2-868e-333ee4560610"
    }
}
```
&nbsp;
- **GET Request**. After all bills paid try pay again. Path: http://localhost:8080/v1/payments/payBills/{subscriberNo} 

>**http://localhost:8080/v1/payments/payBills/b538f97d-3113-46c2-868e-333ee4560610**

- **Response**
```json
{
    "timestamp": "21-07-2022 09:56:07",
    "code": 200,
    "status": "OK",
    "message": "There is no unpaid bill."
}
```
&nbsp;
- **GET Request**. Try to pay when the customer doesn't have an invoice. Path: http://localhost:8080/v1/payments/payBills/{subscriberNo} 

>**http://localhost:8080/v1/payments/payBills/0504da10-54d8-4b4c-bef5-702ac6100527**

- **Response**
```json
{
    "timestamp": "21-07-2022 09:58:47",
    "code": 400,
    "status": "BAD_REQUEST",
    "message": "There is no bill."
}
```
