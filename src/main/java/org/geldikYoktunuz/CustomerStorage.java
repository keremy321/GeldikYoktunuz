package org.geldikYoktunuz;

import java.util.LinkedList;

public class CustomerStorage {
    private static LinkedList<Customer> customerList = new LinkedList<>();
    private static Customer currentCustomer;

    public static void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public static Customer getCustomerById(int customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public static LinkedList<Customer> getAllCustomers() {
        return customerList;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }
}
