package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class PurchaseViewController {

    public ListView<String> lstCompanies;
    public Button btnAddCompany;
    public TextField txtCompanyId;
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
    private TableView<Item> tblOrderBill;

    @FXML
    private TextField txtBillId;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    public void initialize() {

        tblOrderBill.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrderBill.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("batchNo"));
        tblOrderBill.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblOrderBill.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderBill.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderBill.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("price"));

        generateBillId();

        loadAllCompanies();

        //Sort company list by company name
        txtCompanyId.textProperty().addListener((ov, previous, current) -> {
            if (current.isEmpty()) return;
            lstItems.getItems().clear();
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                Statement stm = connection.createStatement();
                String sql = "SELECT * FROM company WHERE company_name LIKE '%1$s'";
                sql = String.format(sql, "%" + current + "%");
                ResultSet rst = stm.executeQuery(sql);

                ObservableList<String> companyList = lstCompanies.getItems();
                companyList.clear();
                while (rst.next()) {
                    companyList.add(rst.getInt(1) + "-" + rst.getString(2));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //Select company and load all items related to it
        lstCompanies.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            if (current == null) return;
            txtCompanyId.setText(current);
            loadItemList(Integer.parseInt(current.split("-")[0]));
        });

        //Get item batch details from the item_code table and add it to cmbBatchNo
        lstItems.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            if (current == null) return;
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                Statement stm = connection.createStatement();
                String sql = "SELECT * FROM item_batch WHERE item_code LIKE '%1$s'";
                sql = String.format(sql, current.substring(0, 4));
                ResultSet rst = stm.executeQuery(sql);
                ObservableList<String> batchNumbers = cmbBatchNo.getItems();
                batchNumbers.clear();

                while (rst.next()) {
                    int batchNo = rst.getInt(2);
                    BigDecimal unitPrice = rst.getBigDecimal(4);
                    batchNumbers.add(batchNo + "-" + unitPrice);
                }
                txtItemId.setText(current);
                txtUnitPrice.clear();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to get item details, try again!").show();
                Platform.exit();
            }
        });

        //Sort Item list by item_code and item_name for selected company
        txtItemId.textProperty().addListener((ov, previous, current) -> {
            Connection connection = DBConnection.getInstance().getConnection();

            try {
                Statement stm = connection.createStatement();
                String sql = "SELECT * FROM item_details WHERE item_code LIKE '%1$s' OR  item_name LIKE '%1$s' HAVING company_id='%2$d'";
                sql = String.format(sql, "%" + current + "%", Integer.parseInt(txtCompanyId.getText().split("-")[0]));
                ResultSet rst = stm.executeQuery(sql);

                ObservableList<String> items = lstItems.getItems();
                items.clear();
                while (rst.next()) {
                    items.add(rst.getString(1) + "-" + rst.getString(2));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //Set unit price to txtUnitPrice according to cmbBatchNo selection
        cmbBatchNo.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            if (current == null) return;
            txtUnitPrice.setText(current.split("-")[1]);
        });

        //Edit bill item details
        tblOrderBill.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            if (current == null) {
                btnCancel.setText("Cancel");
                btnAdd.setText("Add item");
                return;
            }
            txtItemId.setText(current.getItemCode() + "-" + current.getItemName());
            txtUnitPrice.setText(current.getUnitPrice().toString());
            txtQty.setText(String.valueOf(current.getQty()));
            cmbBatchNo.getSelectionModel().select(current.getBatchNo() + "-" + current.getUnitPrice());
            btnCancel.setText("Remove");
            btnAdd.setText("Update");
        });

    }

    //Load all companies list when purchaseView initializing
    private void loadAllCompanies() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM company");
            ObservableList<String> companyList = lstCompanies.getItems();

            while (rst.next()) {
                companyList.add(rst.getInt(1) + "-" + rst.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load company list details, try again!").show();
            Platform.exit();
        }
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

    //Load all item list related to selected company
    private void loadItemList(int companyId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            String sql = "SELECT * FROM item_details WHERE company_id='%1$d'";
            sql = String.format(sql, Integer.parseInt(txtCompanyId.getText().split("-")[0]));
            ResultSet rst = stm.executeQuery(sql);
            ObservableList<String> items = lstItems.getItems();
            items.clear();

            while (rst.next()) {
                items.add(rst.getString(1) + "-" + rst.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load item details, try again!").show();
            Platform.exit();
        }
    }

    public void btnAddCompanyOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (!isDataValid()) return;
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        if (btnAdd.getText().equals("Update")) {
            Item selectedItem = tblOrderBill.getSelectionModel().getSelectedItem();
            int selectedIndex = tblOrderBill.getSelectionModel().getSelectedIndex();
            tblOrderBill.getSelectionModel().clearSelection();
            selectedItem.setItemCode(txtItemId.getText().substring(0, 4));
            selectedItem.setItemName(txtItemId.getText().substring(5));
            selectedItem.setBatchNo(Integer.parseInt(cmbBatchNo.getValue().split("-")[0]));
            selectedItem.setQty(qty);
            selectedItem.setUnitPrice(unitPrice);
            selectedItem.setPrice(unitPrice.multiply(BigDecimal.valueOf(qty)));
            tblOrderBill.refresh();
            btnCancel.fire();
            calculateTotalPrice();
            return;
        }
        Item item = new Item(
                txtItemId.getText().substring(0, 4),
                txtItemId.getText().substring(5),
                Integer.parseInt(cmbBatchNo.getValue().split("-")[0]),
                unitPrice,
                qty,
                unitPrice.multiply(BigDecimal.valueOf(qty))
        );
        ObservableList<Item> billItemList = tblOrderBill.getItems();
        billItemList.add(item);
        calculateTotalPrice();
        btnCancel.fire();
    }

    private boolean isDataValid() {
        boolean isDataValid = true;
        if (!txtUnitPrice.getText().matches("[\\d]+[.][\\d]{2}\\b")) {
            txtUnitPrice.selectAll();
            txtUnitPrice.requestFocus();
            isDataValid = false;
        }
        if (!txtQty.getText().matches("[\\d]+")) {
            txtQty.selectAll();
            txtQty.requestFocus();
            isDataValid = false;
        }
        return isDataValid;
    }

    private void calculateTotalPrice() {
        ObservableList<Item> billItemList = tblOrderBill.getItems();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Item item : billItemList) {
            totalPrice = totalPrice.add(item.getPrice());
        }
        lblTotal.setText(totalPrice.toString());
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        if (btnCancel.getText().equals("Remove")) {
            tblOrderBill.getItems().remove(tblOrderBill.getSelectionModel().getSelectedItem());
            tblOrderBill.refresh();
            tblOrderBill.getSelectionModel().clearSelection();
            calculateTotalPrice();
            cmbBatchNo.getSelectionModel().clearSelection();
            btnCancel.fire();
            return;
        }
        txtItemId.clear();
        cmbBatchNo.getItems().clear();
        lstItems.getItems().clear();
        txtUnitPrice.clear();
        txtQty.clear();
        loadItemList(Integer.parseInt(txtCompanyId.getText().split("-")[0]));

    }

    @FXML
    void btnCancelOrderOnAction(ActionEvent event) {
        tblOrderBill.getItems().clear();
        btnCancel.fire();
        lblTotal.setText("");
        txtCompanyId.clear();
        lstCompanies.getItems().clear();
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        if (tblOrderBill.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please add items into bill before place order").show();
            return;
        }
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stm1 = connection.prepareStatement("INSERT INTO company_order(bill_id, date, company_id, username) VALUES (?,?,?,?)");
            stm1.setInt(1, Integer.parseInt(txtBillId.getText().substring(1)));
            stm1.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stm1.setInt(3, Integer.parseInt(txtCompanyId.getText().split("-")[0]));
            stm1.setString(4, System.getProperty("currentLoggedUser"));
            stm1.executeUpdate();

            PreparedStatement stm = connection.prepareStatement("INSERT INTO supplied_item(bill_id, item_code, batch_no, quantity) VALUES (?,?,?,?)");
            ObservableList<Item> billItemList = tblOrderBill.getItems();
            for (Item item : billItemList) {
                stm.setInt(1, Integer.parseInt(txtBillId.getText().substring(1)));
                stm.setString(2, item.getItemCode());
                stm.setInt(3, item.getBatchNo());
                stm.setInt(4, item.getQty());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (Throwable e) {
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to add bill items, try again!").show();
        } finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
                btnCancelOrder.fire();
                lstItems.getItems().clear();
                new Alert(Alert.AlertType.INFORMATION, "Purchase order placed successfully").show();
                generateBillId();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
