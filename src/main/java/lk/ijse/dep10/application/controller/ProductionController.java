package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductionController {

    public DatePicker dtpStartingDate;
    public Button btnFilter;
    public TextField txtSearch;
    public DatePicker dtpEndDate;
    public TextField txtReferenceNo;
    public RadioButton rdbIn;
    public ToggleGroup In_Out;
    public RadioButton rdbOut;
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

    public void btnFilterOnAction(ActionEvent actionEvent) {
    }
}
