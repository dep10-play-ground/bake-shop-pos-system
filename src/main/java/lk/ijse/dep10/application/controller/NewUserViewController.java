package lk.ijse.dep10.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.model.User;
import lk.ijse.dep10.application.util.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;

public class NewUserViewController {

    public TextField txtUsername;
    public TextField txtContactNumber;
    public TableView<User> tblDetails;
    public Button btnDelete;
    public Button btnSave;
    public TextField txtFullName;
    public TextField txtAddress;
    public Button btnAdd;

    public Button btnRemove;
    public ListView<String> lstContactList;
    @FXML
    private Button btnAddNewUser;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;


    public void initialize(){
        tblDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        loadAllCustomers();

        txtContactNumber.setOnAction(this::btnAddOnAction);
        txtContactNumber.selectionProperty().addListener((ov, previous, current) -> {
            if (current!=null)lstContactList.getSelectionModel().clearSelection();
        });

        btnRemove.setDisable(true);
        lstContactList.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnRemove.setDisable(current==null);
        });

        btnDelete.setDisable(true);
        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnDelete.setDisable(current==null);
            if (current==null)return;
            txtUsername.setText(current.getUserName());
            txtFullName.setText(current.getFullName());
            txtAddress.setText(current.getAddress());
            ObservableList<String> contactList = FXCollections.observableArrayList(current.getContactNumbers());
            lstContactList.setItems(contactList);
        });
    }

    private void loadAllCustomers() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = null;
            stm = connection.createStatement();
            ResultSet rstEmployees = stm.executeQuery("SELECT * FROM Employee");
            PreparedStatement stm2 = connection.prepareStatement("SELECT * FROM Employee_contact WHERE user_name=?");

            while (rstEmployees.next()){
                String userName = rstEmployees.getString("user_name");
                String fullName = rstEmployees.getString("full_name");
                String address = rstEmployees.getString("address");

                ArrayList<String> contactList = new ArrayList<>();

                stm2.setString(1,userName);
                ResultSet rstContacts = stm2.executeQuery();

                while (rstContacts.next()){
                    String contact = rstContacts.getString("contact");
                    contactList.add(contact);
                }

                User user = new User(userName, fullName, contactList, address);
                tblDetails.getItems().add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load Customers").show();
        }

    }

    @FXML
    void btnAddNewUserOnAction(ActionEvent event) {
        txtContactNumber.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtFullName.clear();
        txtAddress.clear();
        lstContactList.getItems().clear();
        tblDetails.getSelectionModel().clearSelection();
        txtUsername.requestFocus();
    }

    public void btnSaveOnAction(){
        if (!isDataValid())return;

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            if (!lstContactList.getItems().isEmpty()){
                String sql = "SELECT * FROM Employee_contact WHERE contact=?";
                PreparedStatement stm = connection.prepareStatement(sql);
                for (String contact : lstContactList.getItems()) {
                    stm.setString(1,contact);
                    if (stm.executeQuery().next()){
                        new Alert(Alert.AlertType.ERROR, contact + " already exists").show();
                        lstContactList.getStyleClass().add("invalid");
                        return;
                    }
                }
            }

            connection.setAutoCommit(false);

            String sql = "INSERT INTO Employee (user_name, password, role, full_name,address) " +
                    "VALUES (?,?,'USER',?,?)";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,txtUsername.getText());
            stm.setString(2,PasswordEncoder.encode(txtPassword.getText()));
            stm.setString(3,txtFullName.getText());
            stm.setString(4,txtAddress.getText());
            stm.executeUpdate();

            if (!lstContactList.getItems().isEmpty()){
                String sql2 = "INSERT INTO Employee_contact (user_name, contact) " +
                        "VALUES (?,?)";
                PreparedStatement stmContact = connection.prepareStatement(sql2);
                for (String contact : lstContactList.getItems()) {
                    stmContact.setString(1,txtUsername.getText());
                    stmContact.setString(2,contact);
                    stmContact.executeUpdate();
                }
            }
            connection.commit();

            User user = new User(txtUsername.getText(),
                    txtFullName.getText(),
                    new ArrayList<>(lstContactList.getItems()),
                    txtAddress.getText()
            );
            tblDetails.getItems().add(user);

            new Alert(Alert.AlertType.INFORMATION,"User Added Successfully..").show();
            btnAddNewUser.fire();

        } catch (SQLException e) {
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to add a new User, Try Again..!").show();
        } finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnDeleteOnAction(){
        User currentSelectedUser = tblDetails.getSelectionModel().getSelectedItem();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Employee WHERE user_name='%s' HAVING role='%s'";
            sql=String.format(sql, currentSelectedUser.getUserName(),"ADMIN");
            ResultSet rst = statement.executeQuery(sql);
            if (rst.next()){
                new Alert(Alert.AlertType.INFORMATION,"Admin can't be Deleted").show();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmContact = connection.prepareStatement("DELETE FROM Employee_contact WHERE user_name=?");
            PreparedStatement stmEmployee = connection.prepareStatement("DELETE FROM Employee WHERE user_name=?");

            stmContact.setString(1,currentSelectedUser.getUserName());
            stmContact.executeUpdate();
            stmEmployee.setString(1, currentSelectedUser.getUserName());
            stmEmployee.executeUpdate();

            connection.commit();
            tblDetails.getItems().remove(currentSelectedUser);
            if (tblDetails.getItems().isEmpty())btnAddNewUser.fire();
        } catch (SQLException e) {
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            new Alert(Alert.AlertType.ERROR,"Failed to Delete the Employee").show();
            e.printStackTrace();
        }finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean isDataValid() {
        boolean dataValid = true;
        for (Node node : new Node[]{txtUsername, txtContactNumber, txtPassword, txtConfirmPassword}) {
            node.getStyleClass().remove("invalid");
        }

        String username = txtUsername.getText();
        String contact = txtContactNumber.getText();
        String password = PasswordEncoder.encode(txtPassword.getText());
        String confirmPassword = txtConfirmPassword.getText();

        if (password.isEmpty() || !PasswordEncoder.matches(confirmPassword,password)) {
            txtConfirmPassword.requestFocus();
            txtConfirmPassword.selectAll();
            txtConfirmPassword.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!password.matches("[A-Za-z0-9]{4,}")) {
            txtPassword.requestFocus();
            txtPassword.selectAll();
            txtPassword.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!username.matches("[A-Za-z0-9]{3,}")) {
            txtUsername.requestFocus();
            txtUsername.selectAll();
            txtUsername.getStyleClass().add("invalid");
            dataValid = false;
        }

        return dataValid;
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (!txtContactNumber.getText().matches("\\d{3}-\\d{7}") ||
                lstContactList.getItems().contains(txtContactNumber.getText().strip())){
            txtContactNumber.requestFocus();
            txtContactNumber.selectAll();
            txtContactNumber.getStyleClass().add("invalid");
        }else{
            txtContactNumber.getStyleClass().remove("invalid");
            lstContactList.getItems().add(txtContactNumber.getText().strip());
            txtContactNumber.requestFocus();
            txtContactNumber.clear();
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        lstContactList.getItems().remove(lstContactList.getSelectionModel().getSelectedItem());
    }
}
