/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Admin
 */
public class customerData {

    private String customerFullName;
    private String customerAddress;

    private String customerPhonenumber;
    private int customerId;

    public customerData(String customerFullName, String customerAddress, String customerPhonenumber, int customerId) {
        this.customerFullName = customerFullName;
        this.customerAddress = customerAddress;
        this.customerPhonenumber = customerPhonenumber;
        this.customerId = customerId;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhonenumber() {
        return customerPhonenumber;
    }

    public void setCustomerPhonenumber(String customerPhonenumber) {
        this.customerPhonenumber = customerPhonenumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "customerData{" + "customerFullName=" + customerFullName + ", customerAddress=" + customerAddress + ", customerPhonenumber=" + customerPhonenumber + ", customerId=" + customerId + '}';
    }
}
