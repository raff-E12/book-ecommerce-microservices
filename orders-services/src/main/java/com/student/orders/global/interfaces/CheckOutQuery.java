package com.student.orders.global.interfaces;

import java.math.BigDecimal;

public class CheckOutQuery {
    private Integer id;
    private String BookName;
    private Integer OrderId;
    private BigDecimal Price;
    private Integer Quantity;
    private BigDecimal SubTotal;
    private String CoverColor;
    private String Author;
    private String Editor;

    // Costruttore
    public CheckOutQuery(
        Integer id, 
        String BookName, 
        Integer OrderId,
        BigDecimal Price,
        Integer Quantity,
        BigDecimal SubTotal,
        String CoverColor,
        String Author,
        String Editor
        ) {
        this.id = id;
        this.BookName = BookName;
        this.OrderId = OrderId;
        this.Price = Price;
        this.Quantity = Quantity;
        this.SubTotal = SubTotal;
        this.CoverColor = CoverColor;
        this.Author = Author;
        this.Editor = Editor;
    }

    // Getter
    public Integer getId() {
        return id;
    }

    public String getBookName() {
        return BookName;
    }

    public Integer getOrderId() {
        return OrderId;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public BigDecimal getSubTotal() {
        return SubTotal;
    }

    public String getCoverColor() {
        return CoverColor;
    }

    public String getAuthor() {
        return Author;
    }

     public String getEditor() {
        return Editor;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }

    public void setOrderId(Integer OrderId) {
        this.OrderId = OrderId;
    }

    public void setPrice(BigDecimal Price) {
        this.Price = Price;
    }

    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    public void setSubTotal(BigDecimal SubTotal) {
        this.SubTotal = SubTotal;
    }
    
    public void setCoverColor(String Color) {
        this.CoverColor = Color;
    }    

    public void setAuthor(String Author) {
        this.Author = Author;
    }    

    public void setEditor(String Editor) {
        this.Editor = Editor;
    }    
}

