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

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpViewController {

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
            String sql = "INSERT INTO Employee (username, password, role, contact) " +
                    "VALUES (?, ?, 'ADMIN', ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, txtUserName.getText());
            stm.setString(2, txtPassword.getText());
            stm.setString(3, txtPassword.getText());
            stm.executeUpdate();

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
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (password.isEmpty() || !password.equals(confirmPassword)) {
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
