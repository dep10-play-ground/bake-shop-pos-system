package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.model.ItemDetail;

public class ItemDetailController {

    public Button btnAddNewItem;
    public TextField txtSearch;
    public ComboBox cmbCompanyName;
    @FXML
    private Button btnRemoveItem;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<ItemDetail> tblItemDetails;

    @FXML
    private TextField txtCompanyCode;
    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtUnitPrice;

    public void initialize(){
        tblItemDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblItemDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("companyCode"));
        tblItemDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tblItemDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));


    }

    @FXML
    void btnRemoveItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    public void btnAddNewItemOnAction(ActionEvent actionEvent) {
//        txtItemCode.setText(itemCodeGenerate());
//        txtItemName.clear();
//        txtCompanyCode.clear();
//        txtUnitPrice.clear();
    }
}

