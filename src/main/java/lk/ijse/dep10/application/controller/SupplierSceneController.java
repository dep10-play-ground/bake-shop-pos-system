package lk.ijse.dep10.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.model.Company;

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
        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contactContact"));
        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("repName"));


        btnRemove.setDisable(true);
        lstContact.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnRemove.setDisable(current == null);
        });

        btnDelete.setDisable(true);
        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnDelete.setDisable(current == null);
        });
    }

    @FXML
    void btnAddNewSupplierOnAction(ActionEvent event) {
        ObservableList<Company> customerList = tblDetails.getItems();
        var newId = customerList.isEmpty()? "1":
                customerList.get(customerList.size() - 1).getCompanyId() + 1;
        txtCompanyId.setText(newId + "");
        txtCompanyName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtEmail.clear();
        txtRefName.clear();
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

    }

}
