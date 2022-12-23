package nt.tshape.automation.selenium;


import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private final Map<String, String> contextAttribute = new HashMap<>();
    private Customer_Information customerInformation;

    public Customer_Information getCustomerInformation() {
        return customerInformation;
    }

    public Customer_Information setCustomerInformation(Customer_Information customerInformation) {
        this.customerInformation = customerInformation;
        return customerInformation;
    }

    public void setAttribute(String key, String value) {
        contextAttribute.put(key, value);
    }

    public String getAttributeByName(String keyName) {
        return contextAttribute.get(keyName);
    }
}
