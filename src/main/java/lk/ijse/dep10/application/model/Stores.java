package lk.ijse.dep10.application.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Stores implements Serializable {
    private int itemCode;
    private int batchNo;
    private  String itemName;
    private String companyName;
    private BigDecimal unitPrice;
    private  int quantity;

    public Stores() {
    }

    public Stores(int itemCode, int batchNo, String itemName, String companyName, BigDecimal unitPrice, int quantity) {
        this.itemCode = itemCode;
        this.batchNo = batchNo;
        this.itemName = itemName;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
