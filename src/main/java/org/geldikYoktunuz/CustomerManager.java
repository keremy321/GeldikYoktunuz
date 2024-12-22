package org.geldikYoktunuz;

import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private static CustomerManager instance;

    private Map<Integer, Customer> customerMap; // Map to store customers by ID
    private Customer currentCustomer; // Currently selected customer

    private CustomerManager() {
        customerMap = new HashMap<>();
    }

    public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerMap.get(customerId);
    }

    public Map<Integer, Customer> getAllCustomers() {
        return customerMap;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
