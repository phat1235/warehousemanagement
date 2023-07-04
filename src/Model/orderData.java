/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class orderData {
    private Integer order_Id;
    private String order_productName;
    private Integer order_quantity;
    private String order_type;
    private double order_price;
    private String order_suplierName;
    private Date  order_date;

    public orderData(Integer order_Id, String order_productName, Integer order_quantity, String order_type, double order_price, String order_suplierName, Date order_date) {
        this.order_Id = order_Id;
        this.order_productName = order_productName;
        this.order_quantity = order_quantity;
        this.order_type = order_type;
        this.order_price = order_price;
        this.order_suplierName = order_suplierName;
        this.order_date = order_date;
    }

    public Integer getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(Integer order_Id) {
        this.order_Id = order_Id;
    }

    public String getOrder_productName() {
        return order_productName;
    }

    public void setOrder_productName(String order_productName) {
        this.order_productName = order_productName;
    }

    public Integer getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(Integer order_quantity) {
        this.order_quantity = order_quantity;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public String getOrder_suplierName() {
        return order_suplierName;
    }

    public void setOrder_suplierName(String order_suplierName) {
        this.order_suplierName = order_suplierName;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    

    
    
    
}
