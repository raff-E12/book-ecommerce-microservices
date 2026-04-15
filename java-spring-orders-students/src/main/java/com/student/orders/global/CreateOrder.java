package com.student.orders.global;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.student.orders.dto.BookTableList;
import com.student.orders.dto.Checkout;

public class CreateOrder {
    
    @JsonProperty("shop")
    private List<BookTableList> Shop;

    @JsonProperty("total")
    private int Total;

    public List<BookTableList> getShop() {
        return Shop;
    }

    public void setShop(List<BookTableList> Shop) {
        this.Shop = Shop;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

}
