package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.db.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PurchaseViewController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private Button btnOrder;

    @FXML
    private ComboBox<String> cmbBatchNo;

    @FXML
    private Label lblTotal;

    @FXML
    private ListView<String> lstItems;

    @FXML
    private TableView<String> tblOrderBill;

    @FXML
    private TextField txtBillId;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    public void initialize() {
        btnCancel.setDisable(true);

        generateBillId();

        loadItemList();

        //Get item batch details from the item_code table and add it to cmbBatchNo
        lstItems.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                Statement stm = connection.createStatement();
                String sql = "SELECT * FROM item_batch WHERE item_code LIKE '%1$s'";
                sql = String.format(sql, Integer.parseInt(current.substring(1, 4)));
                ResultSet rst = stm.executeQuery(sql);
                ObservableList<String> batchNumbers = cmbBatchNo.getItems();
                batchNumbers.clear();

                while (rst.next()) {
                    int batchNo = rst.getInt(2);
                    BigDecimal unitPrice = rst.getBigDecimal(4);
                    batchNumbers.add(batchNo + "-" + unitPrice);
                }
                txtItemId.setText(current);
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to get item details, try again!").show();
                Platform.exit();
            }
        });

        //Sort Item list by item_code and item_name
        txtItemId.textProperty().addListener((ov, previous, current) -> {
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                Statement stm = connection.createStatement();
                String sql = "SELECT * FROM item_details WHERE item_code LIKE '%1$s' OR  item_name LIKE '%1$s'";
                sql = String.format(sql, "%" + current + "%");
                ResultSet rst = stm.executeQuery(sql);

                ObservableList<String> items = lstItems.getItems();
                items.clear();
                while (rst.next()) {
                    items.add(String.format("I%03d", rst.getInt(1)) + "-" + rst.getString(2));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //Set unit price to txtUnitPrice according to cmbBatchNo selection
        cmbBatchNo.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            txtUnitPrice.setText(current.split("-")[1]);
        });

    }

    //Get last billId from company_order table and generate new billId
    private void generateBillId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT MAX(bill_id) FROM company_order");
            rst.next();
            int billId = rst.getInt(1) + 1;
            txtBillId.setText(String.format("B%05d", billId));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate bill id, try again!").show();
            Platform.exit();
        }

    }

    //Load all item list when purchaseView initializing
    private void loadItemList() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM item_details");
            ObservableList<String> items = lstItems.getItems();

            while (rst.next()) {
                items.add(String.format("I%03d", rst.getInt(1)) + "-" + rst.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load item details, try again!").show();
            Platform.exit();
        }
    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        txtItemId.clear();
        cmbBatchNo.getItems().clear();
        txtUnitPrice.clear();
        txtQty.clear();
    }

    @FXML
    void btnCancelOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBatchNoOnAction(ActionEvent event) {

    }

    @FXML
    void txtItemId(ActionEvent event) {

    }

}
