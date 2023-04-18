package lk.ijse.dep10.application.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemDetail implements Serializable {
    private String itemCode;
    private String itemName;
    private String companyCode;
    private String companyName;
    private BigDecimal unitPrice;

    public ItemDetail() {
    }

    public ItemDetail(String itemCode, String itemName, String companyCode, String companyName, BigDecimal unitPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
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
}
