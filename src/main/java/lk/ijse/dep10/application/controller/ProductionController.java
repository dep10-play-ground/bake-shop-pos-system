package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.dep10.application.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductionController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddNewItem;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private ComboBox<?> cmbBatchNo;

    @FXML
    private ListView<?> lstItemList;

    @FXML
    private TableView<?> tblItemDetails;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtOrderID;

    @FXML
    private TextField txtQty;

    public void initialize(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            String sql = "SELECT order_id FROM production_item ORDER BY order_id DESC LIMIT 1";
            ResultSet rst = stm.executeQuery(sql);

            if (rst.next()){
                int lastOrderNumber = rst.getInt(1);
                String newOrderID = String.format("ODR-%03d", lastOrderNumber);
                txtOrderID.setText(newOrderID);
            }else {
                txtOrderID.setText("ODR-001");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddNewItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

}
