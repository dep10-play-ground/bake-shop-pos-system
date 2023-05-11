
package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.Company;
import lk.ijse.dep10.application.model.User;
import lk.ijse.dep10.application.util.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;

public class SupplierSceneController {

    public TextField txtEmail;
    public Button btnUpdate;
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
    private int databaseCompanyId;

    public void initialize(){

        tblDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("companyId"));
        tblDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));

        loadAllCompanies();

        btnRemove.setDisable(true);
        lstContact.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnRemove.setDisable(current == null);
        });

        btnUpdate.setDisable(true);
        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnUpdate.setDisable(current == null);
            btnSave.setDisable(true);

        });

        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            txtCompanyId.setText(current.getCompanyId());
            txtCompanyName.setText(current.getCompanyName());
            txtAddress.setText(current.getAddress());
            lstContact.getItems().clear();
            lstContact.getItems().addAll(current.getCompanyContact());
            txtEmail.setText(current.getEmail());
        });
    }
    private void loadAllCompanies() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM company");
            PreparedStatement stm2 = connection.prepareStatement("SELECT * FROM company_contact WHERE company_id=?");

            ObservableList<Company> companyList = tblDetails.getItems();

        while (rst.next()){
                String companyId = rst.getString("company_id");
                String companyName = rst.getString("company_name");
                String email = rst.getString("email");
                String address = rst.getString("address");

                ArrayList<String> contactList = new ArrayList<>();

                stm2.setString(1,companyId);
                ResultSet rstContacts = stm2.executeQuery();

                while (rstContacts.next()){
                    String contact = rstContacts.getString("contact");
                    contactList.add(contact);
                }

                Company company = new Company(String.format("C%03d",Integer.parseInt(companyId)),companyName,contactList,address,email);
                tblDetails.getItems().add(company);
                tblDetails.getSelectionModel().clearSelection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load companies details, try again!").show();

        }
    }

    @FXML
    void btnAddNewSupplierOnAction(ActionEvent event) {
        btnSave.setDisable(false);
        txtCompanyName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtEmail.clear();
        lstContact.getItems().clear();
        lstContact.getSelectionModel().clearSelection();
        tblDetails.getSelectionModel().clearSelection();
        txtCompanyName.requestFocus();
        txtCompanyName.getStyleClass().remove("invalid");
        txtAddress.getStyleClass().remove("invalid");
        lstContact.getStyleClass().remove("invalid");

        int companyId=findCompanyId();
        databaseCompanyId=companyId;
        String formatedNumber=String.format("C%03d",companyId);

        txtCompanyId.setText(formatedNumber);
    }
    private int findCompanyId(){
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT MAX(company_id) FROM company";
        int maxCompanyId = 0;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                maxCompanyId = resultSet.getInt(1)+1;
                return maxCompanyId;
            }
            return 2;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




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
    void btnUpdateOnAction(ActionEvent event) {
        Company selectedCompany = tblDetails.getSelectionModel().getSelectedItem();
        String selectedCompanyId=selectedCompany.getCompanyId();


            try  {

                Connection connection = DBConnection.getInstance().getConnection();
                String sql2="UPDATE company_contact SET contact=?WHERE company_id=?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setString(1,l);
                preparedStatement2.setInt(2, Integer.parseInt(selectedCompany.getCompanyId().substring(1,(selectedCompany.getCompanyId().length()))));
                preparedStatement2.executeUpdate();

                String sql = "UPDATE company SET company_name=?,email=?,address=? WHERE company_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

       
                preparedStatement.setString(1,txtCompanyName.getText());
                preparedStatement.setString(2,txtEmail.getText());
                preparedStatement.setString(3,txtAddress.getText());
                preparedStatement.setInt(4, Integer.parseInt(selectedCompany.getCompanyId().substring(1,selectedCompany.getCompanyId().length())));


                preparedStatement.executeUpdate();


                tblDetails.getItems().remove(selectedCompany);

                Company company = new Company(selectedCompanyId,
                        txtCompanyName.getText(),
                        new ArrayList<>(lstContact.getItems()),
                        txtAddress.getText(),
                        txtEmail.getText()
                );
                tblDetails.getItems().add(company);
                tblDetails.getSelectionModel().clearSelection();


            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to Update the selected company!").show();
            }

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        lstContact.getItems().remove(lstContact.getSelectionModel().getSelectedItem());

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
//        if (!isDataValid()) return;

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            if (!lstContact.getItems().isEmpty()){
                String sql = "SELECT * FROM company_contact WHERE contact=?";
                PreparedStatement stm = connection.prepareStatement(sql);
                for (String contact : lstContact.getItems()) {
                    stm.setString(1,contact);
                    if (stm.executeQuery().next()){
                        new Alert(Alert.AlertType.ERROR, contact + " already exists").show();
                        lstContact.getStyleClass().add("invalid");
                        return;
                    }
                }
            }

            connection.setAutoCommit(false);

            String sql = "INSERT INTO company (company_id,company_name,email,address) " +
                    "VALUES (?,?,?,?)";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,databaseCompanyId+"");
            stm.setString(2,txtCompanyName.getText());
            stm.setString(3,txtEmail.getText());
            stm.setString(4,txtAddress.getText());
            stm.executeUpdate();

            if (!lstContact.getItems().isEmpty()){
                String sql2 = "INSERT INTO company_contact (company_id, contact) " +
                        "VALUES (?,?)";
                PreparedStatement stmContact = connection.prepareStatement(sql2);
                for (String contact : lstContact.getItems()) {
                    stmContact.setString(1,databaseCompanyId+"");
                    stmContact.setString(2,contact);
                    stmContact.executeUpdate();
                }
            }
            connection.commit();

            Company company = new Company(txtCompanyId.getText(),
                    txtCompanyName.getText(),
                    new ArrayList<>(lstContact.getItems()),
                    txtAddress.getText(),
                    txtEmail.getText()
            );
            tblDetails.getItems().add(company);

            new Alert(Alert.AlertType.INFORMATION,"Company Added Successfully..").show();
            btnAddNewSupplier.fire();

        } catch (SQLException e) {
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to add a new Company, Try Again..!").show();
        } finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    }
    private boolean isDataValid(){
        boolean dataValid = true;
        txtCompanyName.getStyleClass().remove("invalid");
        txtAddress.getStyleClass().remove("invalid");
        lstContact.getStyleClass().remove("invalid");
        txtEmail.getStyleClass().remove("invalid");

        String name = txtCompanyName.getText();
        String address = txtAddress.getText();
        String email=txtEmail.getText();

        if (address.strip().length() < 3){
            txtAddress.requestFocus();
            txtAddress.selectAll();
            txtAddress.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!name.matches("\"[A-Za-z0-9]{3,}")){
            txtCompanyName.requestFocus();
            txtCompanyName.selectAll();
            txtCompanyName.getStyleClass().add("invalid");
            dataValid = false;
        }
        if (!email.matches("[^(.+)@(\\\\S+)$]+")){
            txtCompanyName.requestFocus();
            txtCompanyName.selectAll();
            txtCompanyName.getStyleClass().add("invalid");
            dataValid = false;
        }

        return dataValid;


    }



}
