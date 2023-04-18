package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductionController {

    @FXML
    private Button btnRemoveItem;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<?> cmbCompanyName;

    @FXML
    private ComboBox<?> cmbItemName;

    @FXML
    private TableView<?> tblItemDetails;

    @FXML
    private TextField txtCompanyCode;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnRemoveItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

}
