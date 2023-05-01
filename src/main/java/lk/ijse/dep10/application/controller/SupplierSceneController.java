package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.Company;

import java.io.IOException;
import java.sql.*;

public class SupplierSceneController {

    public TableView<Company> tblDetails;
    public Button btnAddNewSupplier;
    public TextField txtRefContact;
    public TextField txtCompanyId;
    public TextField txtRefName;
    public TextField txtContactNo;
    public TextField txtCompanyName;
    public TextField txtAddress;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    public void initialize() {
        btnDelete.setDisable(true);

        tblDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("companyId"));
        tblDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("companyContact"));
        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repName"));
        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repContact"));

        loadAllCompanies();

        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov,previous,current)->{
            btnDelete.setDisable(current==null);
            if (current==null) return;
            txtAddress.setText(current.getCompanyAddress());
            txtCompanyId.setText(current.getCompanyId());
            txtCompanyName.setText(current.getCompanyName());
            txtContactNo.setText(current.getCompanyContact());
            txtRefName.setText(current.getRepName());
            txtRefContact.setText(current.getRepContact());
        });
    }

    private void loadAllCompanies() {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

//    private boolean isDataValid() {
//
//    }

    public void btnAddNewSupplierOnAction(ActionEvent actionEvent) {
        txtCompanyId.setText(generateId());
        txtCompanyName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        txtRefName.clear();
        txtRefContact.clear();
        tblDetails.getSelectionModel().clearSelection();
        txtCompanyName.requestFocus();
    }

    private String generateId() {
        ObservableList<Company> companyList = tblDetails.getItems();
        if (companyList.isEmpty()) {
            return "C001";
        } else {
            String companyId = companyList.get(companyList.size() - 1).getCompanyId();
            String oldId = companyId.substring(1);
            return String.format("C%03d",Integer.parseInt(oldId)+1);
        }
    }


}
