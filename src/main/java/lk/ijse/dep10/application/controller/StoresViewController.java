package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.Item;
import lk.ijse.dep10.application.model.Stores;

import java.math.BigDecimal;
import java.sql.*;

public class StoresViewController {

    @FXML
    private TableView<Item> tblStores;

    @FXML
    private TextField txtSearch;

    public void initialize(){
        Platform.runLater(()->{
            txtSearch.requestFocus();
        });
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            Statement stm = connection.createStatement();
//            String sql="CREATE TEMPORARY TABLE StoresView SELECT item_batch.item_code As item_code,batch_no,quantity,unit_price,item_name,item_details.company_id,company.company_name FROM item_batch INNER JOIN item_details ON item_batch.item_code = item_details.item_code "+
//            "INNER JOIN company ON item_details.company_id = company.company_id";
//            stm.execute(sql);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        tblStores.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblStores.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("batchNo"));
        tblStores.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblStores.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tblStores.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblStores.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("qty"));

        txtSearch.textProperty().addListener((ov,previous,current)->{
            Connection connection1 = DBConnection.getInstance().getConnection();
            try {
                Statement stm2 = connection1.createStatement();
                String sql="SELECT * FROM (SELECT item_batch.item_code As item_code,batch_no,quantity,unit_price,item_name,item_details.company_id,company.company_name FROM item_batch INNER JOIN item_details ON item_batch.item_code = item_details.item_code "+
                        "INNER JOIN company ON item_details.company_id = company.company_id) AS StoresView WHERE item_code LIKE '%1$s' OR item_name LIKE '%1$s' OR company_name LIKE '%1$s'";
                sql=String.format(sql,"%"+current+"%");
                ResultSet rst = stm2.executeQuery(sql);
                ObservableList<Item> itemList = tblStores.getItems();
                itemList.clear();
                while (rst.next()){
                    String itemCode=rst.getString("item_code");
                    String itemName=rst.getString("item_name");
                    int batchNo=rst.getInt("batch_no");
                    int quantity=rst.getInt("quantity");
                    BigDecimal unitPrice=rst.getBigDecimal("unit_price");
                    String companyName=rst.getString("company_name");
                    itemList.add(new Item(itemCode,itemName,batchNo,companyName,unitPrice,quantity));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        loadItems();
    }
private void loadItems(){
    Connection connection = DBConnection.getInstance().getConnection();
    try {
        Statement stm1 = connection.createStatement();
        ResultSet rst1 = stm1.executeQuery("SELECT * FROM item_batch");
        PreparedStatement stm2 = connection.prepareStatement("SELECT * FROM item_details WHERE item_code=?");
        PreparedStatement stm3 = connection.prepareStatement("SELECT company_name FROM company WHERE company_id=?");
        while (rst1.next()){
            String itemCode=rst1.getString("item_code");
            int batchNo=rst1.getInt("batch_no");
            int quantity=rst1.getInt("quantity");
            BigDecimal unitPrice=rst1.getBigDecimal("unit_price");
            stm2.setString(1,itemCode);
            ResultSet rst2 = stm2.executeQuery();
            rst2.next();
            String itemName=rst2.getString("item_name");
            int companyId=rst2.getInt("company_id");
            stm3.setInt(1,companyId);
            ResultSet rst3 = stm3.executeQuery();
            rst3.next();
            String companyName=rst3.getString("company_name");
            Item stores=new Item(itemCode,itemName,batchNo,companyName,unitPrice,quantity);
            tblStores.getItems().add(stores);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

}
