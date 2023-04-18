package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplierSceneController {

    public TableView tblDetails;
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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }


    public void btnAddNewSupplierOnAction(ActionEvent actionEvent) {
    }

}
