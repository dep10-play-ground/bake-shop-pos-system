package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupplierSceneController {

    public TextField txtEmail;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddNewSupplier;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnSave;



    @FXML
    private ListView<String> lstContact;

    @FXML
    private TableView<Company> tblDetails;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCompanyId;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtRefName;

    public void initialize(){
        tblDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("companyId"));
        tblDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contactContact"));
        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("email"));

        loadAllCompanies();

        btnRemove.setDisable(true);
        lstContact.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnRemove.setDisable(current == null);
        });

        btnDelete.setDisable(true);
        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnDelete.setDisable(current == null);
        });
    }
    private void loadAllCompanies() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM company");
            ObservableList<Company> companyList = tblDetails.getItems();

//            while (rst.next()) {
//                String companyId = rst.getString("company_id");
//                String companyName = rst.getString("company_name");
//                String companyAddress = rst.getString("company_address");
//                ArrayList<String> companyContact = rst.getString("company_contact");
//                String companyEmail = rst.getString("company_email");
//                companyList.add(new Company(companyId,companyName,companyAddress,companyContact,companyEmail));
//            }
            Platform.runLater(btnAddNewSupplier::fire);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load companies details, try again!").show();
            Platform.exit();
        }
    }

    @FXML
    void btnAddNewSupplierOnAction(ActionEvent event) {
        ObservableList<Company> customerList = tblDetails.getItems();
        var newId = customerList.isEmpty()? "1":
                customerList.get(customerList.size() - 1).getCompanyId() + 1;
        txtCompanyId.setText("C"+newId + "");
        txtCompanyName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtEmail.clear();
        lstContact.getItems().clear();
        lstContact.getSelectionModel().clearSelection();
        tblDetails.getSelectionModel().clearSelection();
        txtCompanyName.requestFocus();
        txtCompanyName.getStyleClass().remove("invalid");
        txtAddress.getStyleClass().remove("invalid");
        lstContact.getStyleClass().remove("invalid");

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (!txtContactNo.getText().matches("\\d{3}-\\d{7}") ||
                lstContact.getItems().contains(txtContactNo.getText().strip())){
            txtContactNo.requestFocus();
            txtContactNo.selectAll();
            txtContactNo.getStyleClass().add("invalid");
        }else{
            txtContactNo.getStyleClass().remove("invalid");
            lstContact.getItems().add(txtContactNo.getText().strip());
            txtContactNo.requestFocus();
            txtContactNo.clear();
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        lstContact.getItems().remove(lstContact.getSelectionModel().getSelectedItem());

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid()) return;

    }
    private boolean isDataValid(){
        boolean dataValid = true;
        txtCompanyName.getStyleClass().remove("invalid");
        txtAddress.getStyleClass().remove("invalid");
        lstContact.getStyleClass().remove("invalid");
        txtEmail.getStyleClass().remove("invalid");

        String name = txtCompanyName.getText();
        String address = txtAddress.getText();
        String email=txtEmail.getText();

        if (address.strip().length() < 3){
            txtAddress.requestFocus();
            txtAddress.selectAll();
            txtAddress.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!name.matches("[A-Za-z ]+")){
            txtCompanyName.requestFocus();
            txtCompanyName.selectAll();
            txtCompanyName.getStyleClass().add("invalid");
            dataValid = false;
        }
        if (!email.matches("[^(.+)@(\\\\S+)$]+")){
            txtCompanyName.requestFocus();
            txtCompanyName.selectAll();
            txtCompanyName.getStyleClass().add("invalid");
            dataValid = false;
        }

        return dataValid;
    }

}
