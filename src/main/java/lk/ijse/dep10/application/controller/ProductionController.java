package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.Item;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ProductionController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private ComboBox<String> cmbBatchNo;

    @FXML
    private ListView<String> lstItemList;

    @FXML
    private TableView<Item> tblItemDetails;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtOrderID;

    @FXML
    private TextField txtQty;

    public void initialize(){

        tblItemDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("batchNo"));
        tblItemDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblItemDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        Connection connection = DBConnection.getInstance().getConnection();
        generateOrderId();

        try {
            Statement stm = connection.createStatement();
            String sql = "SELECT CONCAT('I',LPAD(item_code,3,'0')) as item_id,item_name FROM item_details";
            ResultSet rst = stm.executeQuery(sql);
            ArrayList<String> itemList = new ArrayList<>();
            while (rst.next()){
                String itemCode = rst.getString(1);
                String itemName = rst.getString(2);
                String item = itemCode+" - "+itemName;
                itemList.add(item);
            }
            lstItemList.getItems().addAll(itemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtItemID.textProperty().addListener((ov, previous, current) -> {
            if (current==null)cmbBatchNo.getItems().clear();

            Connection connection1 = DBConnection.getInstance().getConnection();
            try {
                Statement stm = connection1.createStatement();
                String sql = "SELECT item_code,item_name FROM " +
                        "(SELECT CONCAT('I',LPAD(item_code,3,0)) AS item_code,item_name FROM item_details) AS T" +
                        " WHERE item_code LIKE '%1$s' OR item_name LIKE '%1$s'";
                sql = String.format(sql, "%" + current + "%");
                ResultSet rst = stm.executeQuery(sql);

                ArrayList<String> itemList = new ArrayList<>();

                while (rst.next()){
                    String itemCode = rst.getString(1);
                    String itemName = rst.getString(2);
                    itemList.add(itemCode + " - " + itemName);
                }
                Platform.runLater(()->{
                    lstItemList.setItems(FXCollections.observableArrayList(itemList));
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        lstItemList.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            if (current==null){
                cmbBatchNo.getSelectionModel().clearSelection();
                return;
            }
            String itemID = current.substring(1, 4);
            txtItemID.setText(current);

            loadBatchNoList(Integer.parseInt(itemID));
        });

        cmbBatchNo.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            if (current==null)return;
            cmbBatchNo.setValue(current);
        });

        tblItemDetails.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            if (current==null){
                cmbBatchNo.setValue(null);
                return;
            }

            btnAdd.setText("Update");
            btnCancel.setText("Remove");

            txtItemID.setText(current.getItemCode()+" - "+current.getItemName());
            txtItemID.setEditable(false);
            cmbBatchNo.setValue(current.getBatchNo()+"");
            txtQty.setText(current.getQty()+"");


            int selectedItemCode = Integer.parseInt(current.getItemCode().substring(1,4));
            loadBatchNoList(selectedItemCode);

        });
    }

    private void generateOrderId() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            String sql = "SELECT order_id FROM production_item ORDER BY order_id DESC LIMIT 1";
            ResultSet rst = stm.executeQuery(sql);

            if (rst.next()){
                int lastOrderNumber = rst.getInt(1);
                String newOrderID = String.format("ODR-%03d", lastOrderNumber+1);
                txtOrderID.setText(newOrderID);
            }else {
                txtOrderID.setText("ODR-001");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (!isDataValid())return;

        String itemID = txtItemID.getText().substring(0, 4);
        int batchNo = Integer.parseInt(cmbBatchNo.getSelectionModel().getSelectedItem());
        int qty = Integer.parseInt(txtQty.getText());
        String itemName = txtItemID.getText().substring(7);

        if (btnAdd.getText().equals("Add")) {
            for (Item item : tblItemDetails.getItems()) {
                if (item.getItemCode().equals(itemID) && item.getBatchNo() == batchNo) {
                    int newQty = item.getQty() + qty;
                    Connection connection = DBConnection.getInstance().getConnection();
                    try {
                        PreparedStatement stm = connection.prepareStatement("SELECT quantity FROM item_batch WHERE item_code=? AND batch_no=?");
                        stm.setInt(1, Integer.parseInt(item.getItemCode().substring(1, 4)));
                        stm.setInt(2, batchNo);
                        ResultSet rst = stm.executeQuery();
                        if (rst.next()) {
                            int dbQty = rst.getInt(1);
                            if (dbQty < newQty) {
                                new Alert(Alert.AlertType.ERROR, "Available Qty in Stock : " + (dbQty - item.getQty()) + "").show();
                                txtQty.selectAll();
                                txtQty.requestFocus();
                                return;
                            }
                            item.setQty(newQty);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    tblItemDetails.refresh();
                    txtItemID.clear();
                    txtQty.clear();
                    cmbBatchNo.getItems().clear();
                    return;
                }
            }

            Item item = new Item(itemID, itemName, batchNo, qty);
            tblItemDetails.getItems().add(item);

            txtItemID.clear();
            txtQty.clear();
            cmbBatchNo.getItems().clear();
        }
        else if (btnAdd.getText().equals("Update")) {
            Item selectedItem = tblItemDetails.getSelectionModel().getSelectedItem();
            int itemQty= Integer.parseInt(txtQty.getText());
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement stm = connection.prepareStatement("SELECT quantity FROM item_batch WHERE item_code=? AND batch_no=?");
                stm.setInt(1, Integer.parseInt(selectedItem.getItemCode().substring(1, 4)));
                stm.setInt(2, Integer.parseInt(cmbBatchNo.getValue()));
                ResultSet rst = stm.executeQuery();
                if (rst.next()) {
                    int dbQty = rst.getInt(1);
                    if (dbQty < itemQty) {
                        new Alert(Alert.AlertType.ERROR, "Available Qty in Stock : " + dbQty + "").show();
                        txtQty.selectAll();
                        txtQty.requestFocus();
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            selectedItem.setQty(itemQty);
                selectedItem.setBatchNo(Integer.parseInt(cmbBatchNo.getValue()));
                tblItemDetails.refresh();
            txtItemID.clear();
            txtQty.clear();
            cmbBatchNo.getItems().clear();
            txtItemID.requestFocus();
            tblItemDetails.getSelectionModel().clearSelection();

            txtItemID.setEditable(true);
            btnAdd.setText("Add");
            btnCancel.setText("Cancel");
        }
        txtItemID.requestFocus();
    }

    private boolean isDataValid() {
        boolean dataValid = true;

        if (txtItemID.getText().toCharArray().length<4 ||
                !txtItemID.getText().matches("[I][\\d]{3}[\\w\\s-]+")){
            txtItemID.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Select a Item From The List").show();
            return false;
        }

        if (txtQty.getText().isEmpty() || !txtQty.getText().matches("[\\d]+")){
            txtQty.requestFocus();
            dataValid=false;
        }

        if (cmbBatchNo.getValue() ==null){
            cmbBatchNo.requestFocus();
            dataValid = false;
        }

        if (!txtItemID.getText().isEmpty() && !cmbBatchNo.getSelectionModel().isEmpty() &&
                !txtQty.getText().trim().isEmpty()) {
            String itemCode = txtItemID.getText().substring(1, 4);
            int batchNo = Integer.parseInt(cmbBatchNo.getSelectionModel().getSelectedItem());
            String sql = "SELECT quantity FROM item_batch WHERE item_code='%s' AND batch_no='%d'";
            sql = String.format(sql, itemCode, batchNo);
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                Statement stm = connection.createStatement();
                ResultSet rst = stm.executeQuery(sql);
                rst.next();
                int storeItemQty = rst.getInt(1);
                int filledItemQty = Integer.parseInt(txtQty.getText());
                if (filledItemQty>storeItemQty) {
                    dataValid = false;
                    txtQty.selectAll();
                    txtQty.requestFocus();
                    new Alert(Alert.AlertType.ERROR,"Available Qty On Store : "+storeItemQty+"").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        return dataValid;
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        if (btnCancel.getText().equals("Remove")){
            tblItemDetails.getItems().remove(tblItemDetails.getSelectionModel().getSelectedItem());
            btnCancel.setText("Cancel");
            btnAdd.setText("Add");
        }
        txtItemID.clear();
        txtQty.clear();
        cmbBatchNo.setValue(null);
        cmbBatchNo.getSelectionModel().clearSelection();
        tblItemDetails.getSelectionModel().clearSelection();
        txtItemID.requestFocus();
    }

    @FXML
    void btnCancelOrderOnAction(ActionEvent event) {
        tblItemDetails.getItems().clear();
        tblItemDetails.refresh();
        txtItemID.requestFocus();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (tblItemDetails.getItems().isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Item List is Empty").show();
            return;
        }

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO production_item ( date, user_name) VALUES (?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1,Timestamp.valueOf(LocalDateTime.now()));
            stm.setString(2,System.getProperty("currentLoggedUser"));
            stm.executeUpdate();


            PreparedStatement stmAddItem = connection.prepareStatement("" +
                    "INSERT INTO production_item_details (order_id, item_code, batch_no, quantity) VALUES (?,?,?,?)");

            PreparedStatement stmReduceItem = connection.prepareStatement("" +
                    "UPDATE item_batch SET quantity=? WHERE item_code=? AND batch_no=?");
            PreparedStatement stmGetQty = connection.prepareStatement("" +
                    "SELECT quantity FROM item_batch WHERE item_code=? AND batch_no=?");

            int orderId = Integer.parseInt(txtOrderID.getText().substring(4));

            for (Item item : tblItemDetails.getItems()) {
                String itemID = item.getItemCode().substring(1,4);
                int batchNo = item.getBatchNo();
                int qty = item.getQty();

                stmAddItem.setInt(1,orderId);
                stmAddItem.setString(2,itemID);
                stmAddItem.setInt(3,batchNo);
                stmAddItem.setInt(4,qty);
                stmAddItem.executeUpdate();

                stmGetQty.setString(1,itemID);
                stmGetQty.setInt(2,batchNo);
                ResultSet rst = stmGetQty.executeQuery();
                rst.next();
                int dbQty = rst.getInt(1);

                stmReduceItem.setInt(1,dbQty-qty);
                stmReduceItem.setString(2,itemID);
                stmReduceItem.setInt(3,batchNo);
                stmReduceItem.executeUpdate();
            }
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION,"Order placed successfully").show();

            tblItemDetails.getItems().clear();
            generateOrderId();

        } catch (Throwable e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            new Alert(Alert.AlertType.ERROR,"Some Error occurred").show();
            throw new RuntimeException(e);
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadBatchNoList(int itemCode){
        Connection connection2 = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stm = connection2.prepareStatement("SELECT batch_no FROM item_batch WHERE item_code=?");
            stm.setInt(1,itemCode);
            ResultSet rst = stm.executeQuery();
            ArrayList<String> batchNoList = new ArrayList<>();
            while (rst.next()){
                String batchNo = rst.getString(1);
                batchNoList.add(batchNo);
            }
            cmbBatchNo.getItems().clear();
            cmbBatchNo.getItems().addAll(batchNoList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void qtyValidation(int itemCode, int batchNo, int newQty){
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            PreparedStatement stm = connection.prepareStatement("SELECT quantity FROM item_batch WHERE item_code=? AND batch_no=?");
//            stm.setInt(1, itemCode);
//            stm.setInt(2, batchNo);
//            ResultSet rst = stm.executeQuery();
//            if (rst.next()) {
//                int dbQty = rst.getInt(1);
//                if (dbQty < newQty) {
//                    new Alert(Alert.AlertType.ERROR, "Available Qty in Stock : " + (dbQty - item.getQty()) + "").show();
//                    txtQty.selectAll();
//                    txtQty.requestFocus();
//                    return;
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
