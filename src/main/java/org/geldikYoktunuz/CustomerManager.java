package org.geldikYoktunuz;

import java.util.LinkedList;

public class CustomerManager {
    private static CustomerManager instance;

    private LinkedList<Customer> customerList; // LinkedList to store customers
    private Customer currentCustomer; // Currently selected customer

    private CustomerManager() {
        customerList = new LinkedList<>(); // Initialize as LinkedList
    }

    public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer); // Add customer to the LinkedList
    }

    public Customer getCustomerById(int customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null; // Return null if not found
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