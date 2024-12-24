package org.geldikYoktunuz;

import java.util.LinkedList;

public class CustomerManager {
    private static CustomerManager instance;

    private LinkedList<Customer> customerList;
    private Customer currentCustomer;

    private CustomerManager() {
        customerList = new LinkedList<>();
    }

    public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public Customer getCustomerById(int customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public LinkedList<Customer> getAllCustomers() {
        return customerList;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}