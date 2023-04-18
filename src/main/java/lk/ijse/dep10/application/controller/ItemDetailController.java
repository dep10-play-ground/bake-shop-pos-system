package lk.ijse.dep10.application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.ItemDetail;

import java.math.BigDecimal;
import java.sql.*;

public class ItemDetailController {

    public Button btnAddNewItem;
    public TextField txtSearch;
    public ComboBox <String> cmbCompanyName;
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
        loadData();
        tblItemDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblItemDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tblItemDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        loadCompanyName();
        tblItemDetails.getSelectionModel().selectedItemProperty().addListener((value,ol,current)->{
            if(current==null)return;
            txtItemName.setText(current.getItemName());
            txtItemCode.setText(current.getItemCode());
            txtUnitPrice.setText(current.getUnitPrice()+"");
            txtCompanyCode.setText(current.getCompanyCode());
            cmbCompanyName.setValue(current.getCompanyName());
        });

        cmbCompanyName.getSelectionModel().selectedItemProperty().addListener((value,old,current)->{
            if(current==null)return;
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement stm = connection.prepareStatement("SELECT company_id FROM Company_Details WHERE company_name=?");
                stm.setString(1,current);
                ResultSet rst = stm.executeQuery();
                rst.next();
                String companyCode = rst.getString("company_id");
                txtCompanyCode.setText(companyCode);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void loadData(){
        Connection connection = DBConnection.getInstance().getConnection();
        String sql="Select * FROM Item";
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(sql);
            PreparedStatement stm2 = connection.prepareStatement("SELECT company_name FROM Company_Details WHERE company_id=?");
            while(rst.next()){
                String itemCode=rst.getString("item_code");
                String itemName=rst.getString("item_name");
                BigDecimal unitPrice=rst.getBigDecimal("unit_price");
                String companyId = rst.getString("company_id");
                stm2.setString(1,companyId);
                ResultSet rst2 = stm2.executeQuery();
                rst2.next();
                String companyName=rst2.getString("company_name");
                ItemDetail loadItem=new ItemDetail(itemCode,itemName,companyId,companyName,unitPrice);
                tblItemDetails.getItems().add(loadItem);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadCompanyName(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT company_name FROM Company_Details");
            while (rst.next()){
                cmbCompanyName.getItems().add(rst.getString("company_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnRemoveItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(!isDataValid())return;

    }
    private boolean isDataValid(){
        boolean dataValid=true;
        if(txtUnitPrice.getText().isEmpty() || !(txtUnitPrice.getText().matches("\\d{1,}"))){
            txtUnitPrice.requestFocus();
            dataValid=false;
        }
        if(txtCompanyCode.getText().isEmpty() || cmbCompanyName.getValue().isEmpty()){
            txtCompanyCode.requestFocus();
            dataValid=false;
        }
        if(txtItemName.getText().isEmpty() || !(txtItemName.getText().matches("[A-Za-z]+"))){
            txtItemName.requestFocus();
            dataValid=false;
        }
        return dataValid;
    }

    public void btnAddNewItemOnAction(ActionEvent actionEvent) {
        txtItemCode.setText(itemCodeGenerate());
        txtItemName.clear();
        txtCompanyCode.clear();
        txtUnitPrice.clear();
        tblItemDetails.getSelectionModel().clearSelection();

    }
    private String itemCodeGenerate(){
       return tblItemDetails.getItems().isEmpty()?"I001":String.format("I%03d",Integer.parseInt(tblItemDetails.getItems().
                get(tblItemDetails.getItems().size()-1).getItemCode().substring(1))+1);

    }

}

