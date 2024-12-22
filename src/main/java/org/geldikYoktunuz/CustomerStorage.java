package org.geldikYoktunuz;

import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private static Map<Integer, Customer> customerMap = new HashMap<>();
    private static Customer currentCustomer;

    public static void addCustomer(Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
    }

    public static Customer getCustomerById(int customerId) {
        return customerMap.get(customerId);
    }

    public static Map<Integer, Customer> getAllCustomers() {
        return customerMap;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }
}
