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
import lk.ijse.dep10.application.util.UserRole;

import java.sql.*;
import java.util.ArrayList;

public class UserSceneController {

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
    public RadioButton rdoAdmin;
    public RadioButton rdoOther;
    public ToggleGroup user;
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
        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("userRole"));
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
            if(current.userRole==UserRole.ADMIN){
                rdoAdmin.getToggleGroup().selectToggle(rdoAdmin);
            }else {
                rdoOther.getToggleGroup().selectToggle(rdoOther);
            }
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
                UserRole role = UserRole.valueOf(rstEmployees.getString("role"));

                ArrayList<String>  contactList = new ArrayList<>();

                stm2.setString(1,userName);
                ResultSet rstContacts = stm2.executeQuery();

                while (rstContacts.next()){
                    String contact = rstContacts.getString("contact");
                    contactList.add(contact);
                }

                User user = new User(userName, fullName, contactList, address , role );
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
        rdoAdmin.getToggleGroup().selectToggle(null);

    }

    public void btnSaveOnAction(){

        if (!isDataValid()){
          new Alert(Alert.AlertType.ERROR, "Invalid Data! Try Again!").showAndWait();
          return;
        }

        try {
            Connection connection = DBConnection.getInstance().getConnection();



            connection.setAutoCommit(false);

            User user = new User(txtUsername.getText(),
                    txtFullName.getText(),
                    new ArrayList<>(lstContactList.getItems()),
                    txtAddress.getText(), rdoAdmin.isSelected()?UserRole.ADMIN:UserRole.USER);


            User selectedUser = tblDetails.getSelectionModel().getSelectedItem();

            if (selectedUser == null) {

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

                String sql = "INSERT INTO Employee (user_name, password, role, full_name,address) " +
                        "VALUES (?,?,?,?,?)";

                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, user.getUserName());
                stm.setString(2, PasswordEncoder.encode(txtPassword.getText()));
                stm.setString(3, user.getUserRole().toString());
                stm.setString(4, user.getFullName());
                stm.setString(5, user.getAddress());
                stm.executeUpdate();

                tblDetails.getItems().add(user);

                if (!lstContactList.getItems().isEmpty()){
                    String sql3 = "INSERT INTO Employee_contact (user_name, contact) " +
                            "VALUES (?,?)";
                    PreparedStatement stmContact = connection.prepareStatement(sql3);
                    for (String contact : lstContactList.getItems()) {
                        stmContact.setString(1,txtUsername.getText());
                        stmContact.setString(2,contact);
                        stmContact.executeUpdate();
                    }
                }
                connection.commit();

                new Alert(Alert.AlertType.INFORMATION,"User Added Successfully..").show();
                btnAddNewUser.fire();
            }else {
                txtUsername.setEditable(false);
                String sql2 = "Update Employee SET full_name=?,address=?, role=? ,password=? WHERE user_name=?";
                PreparedStatement stmUpdate = connection.prepareStatement(sql2);
                stmUpdate.setString(1, user.getFullName());
                stmUpdate.setString(2, user.getAddress());
                stmUpdate.setString(3,user.getUserRole().toString());
                stmUpdate.setString(4,PasswordEncoder.encode(txtPassword.getText()));
                stmUpdate.setString(5, user.getUserName());
                stmUpdate.executeUpdate();
                tblDetails.getItems().remove(tblDetails.getSelectionModel().getSelectedItem());
                tblDetails.getItems().add(user);
                new Alert(Alert.AlertType.CONFIRMATION, "User "+ user.getUserName() + " Modified Successfully").show();


            }



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

        if (!password.matches("[A-Za-z0-9]{4,}") ) {
            txtPassword.requestFocus();
            txtPassword.selectAll();
            txtPassword.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!username.matches("[A-Za-z0-9]{3,}") ||username.isEmpty()) {
            txtUsername.requestFocus();
            txtUsername.selectAll();
            txtUsername.getStyleClass().add("invalid");
            dataValid = false;
        }
        if(rdoAdmin.getToggleGroup()==null){
            dataValid = false;
            rdoAdmin.requestFocus();
            rdoAdmin.getStyleClass().add("invalid");
        }
        if(txtFullName.getText().isEmpty()){
            dataValid = false;
            txtFullName.getStyleClass().add("invalid");
            txtFullName.requestFocus();
        }

        if(lstContactList.getItems().isEmpty()){
            dataValid = false;
            txtContactNumber.getStyleClass().add("invalid");
            txtContactNumber.requestFocus();
        }
        if(txtAddress.getText().isEmpty() || txtAddress.getText().length()<3){
            dataValid = false;
            txtAddress.getStyleClass().add("invalid");
            txtAddress.requestFocus();
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
