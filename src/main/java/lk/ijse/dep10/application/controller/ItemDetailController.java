package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ItemDetailController {

    public Button btnAddNewItem;
    public TextField txtSearch;
    public ComboBox cmbCompanyName;
    @FXML
    private Button btnRemoveItem;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<?> tblItemDetails;

    @FXML
    private TextField txtCompanyCode;
    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnRemoveItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    public void btnAddNewItemOnAction(ActionEvent actionEvent) {
    }
}

