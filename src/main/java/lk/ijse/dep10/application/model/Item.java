package lk.ijse.dep10.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    private String itemCode;
    private String itemName;
    private int batchNo;
    private String companyID;
    private String companyName;
    private BigDecimal unitPrice;
    private int qty;
    private BigDecimal price;



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

}
