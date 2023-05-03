package lk.ijse.dep10.application.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {
    private String itemCode;
    private String itemName;
    private int batchNo;
    private String companyID;
    private String companyName;
    private BigDecimal unitPrice;
    private int qty;
    private BigDecimal price;

    public Item() {
    }


    public Item(String itemCode, String itemName, int batchNo, BigDecimal unitPrice, int qty, BigDecimal price) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchNo = batchNo;
        this.qty = qty;
        this.price = price;
    }

    public Item(String itemCode, String itemName, int batchNo, BigDecimal unitPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchNo = batchNo;
        this.unitPrice = unitPrice;
    }

    public Item(String itemCode, String itemName, int batchNo, int qty) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchNo = batchNo;
        this.qty = qty;
    }

    public Item(String itemCode, String itemName, int batchNo, String companyID, String companyName, BigDecimal unitPrice, int qty) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchNo = batchNo;
        this.companyID = companyID;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public Item(String itemCode, String itemName, int batchNo, String companyName, BigDecimal unitPrice, int qty) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchNo = batchNo;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
