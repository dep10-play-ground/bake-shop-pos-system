package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.dep10.application.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddNewItemController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddNewCompany;

    @FXML
    private Button btnCancel;

    @FXML
    private ListView<String> lstCompanyList;

    @FXML
    private TextField txtCompany;

    @FXML
    private TextField txtItemName;

    public void initialize(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT CONCAT('C',LPAD(company_id,3,'0')) AS company_id,company_name FROM company");
            ArrayList<String> companyList = new ArrayList<>();
            while (rst.next()){
                String companyId = rst.getString(1);
                String companyName = rst.getString(2);
                String company = companyId + " - " + companyName;
                companyList.add(company);
            }
            lstCompanyList.getItems().addAll(companyList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddNewCompanyOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

}