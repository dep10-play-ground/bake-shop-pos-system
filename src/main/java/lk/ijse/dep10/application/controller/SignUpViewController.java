package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.util.PasswordEncoder;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpViewController {

    public TextField txtFullName;
    public TextField txtAddress;
    @FXML
    private Button btnCreate;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtContact;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnCreateOnAction(ActionEvent event) {
        if (!isDataValid()) return;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO Employee (user_name, password, role,full_name,address) " +
                    "VALUES (?, ?, 'ADMIN',?,?)";
            connection.setAutoCommit(false);

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, txtUserName.getText());
            stm.setString(2, PasswordEncoder.encode(txtPassword.getText()));
            stm.setString(3,txtFullName.getText());
            stm.setString(4,txtAddress.getText());
            stm.executeUpdate();
            String sql2 = "INSERT INTO Employee_contact (user_name, contact) " +
                    "VALUES (?,?)";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setString(1,txtUserName.getText());
            stm2.setString(2,txtContact.getText());
            stm2.executeUpdate();

            connection.commit();

            URL loginView = getClass().getResource("/view/LoginView.fxml");
            var loginScene = new Scene(FXMLLoader.load(loginView));
            Stage stage = (Stage) btnCreate.getScene().getWindow();
            stage.setScene(loginScene);
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to create the admin user account, try again!").show();
        }

    }

    private boolean isDataValid() {
        boolean dataValid = true;
        for (Node node : new Node[]{txtUserName, txtContact, txtPassword, txtConfirmPassword}) {
            node.getStyleClass().remove("invalid");
        }

        String username = txtUserName.getText();
        String contact = txtContact.getText();
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
            txtUserName.requestFocus();
            txtUserName.selectAll();
            txtUserName.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!contact.matches("\\d{3}-\\d{7}")) {
            txtContact.requestFocus();
            txtContact.selectAll();
            txtContact.getStyleClass().add("invalid");
            dataValid = false;
        }
        return dataValid;
    }

}
