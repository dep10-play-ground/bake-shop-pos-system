package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.Company;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SupplierSceneController {

    public TableView<Company> tblDetails;
    public Button btnAddNewSupplier;
    public TextField txtRefContact;
    public TextField txtCompanyId;
    public TextField txtRefName;
    public TextField txtContactNo;
    public TextField txtCompanyName;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    public void initialize() {
        tblDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("companyId"));
        tblDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("companyContact"));
        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repName"));
        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repContact"));

        loadAllCompanies();
    }

    private void loadAllCompanies() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
//            stm.executeQuery("SELECT * FROM ")
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }


    public void btnAddNewSupplierOnAction(ActionEvent actionEvent) {
    }

}
