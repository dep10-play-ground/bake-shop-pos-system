package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dep10.application.db.DBConnection;
import lk.ijse.dep10.application.util.PasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginViewController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException, SQLException {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM Employee WHERE user_name=? AND password=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, PasswordEncoder.encode(password));
            ResultSet rst = stm.executeQuery();

            if (!rst.next()){
                txtUserName.getStyleClass().add("invalid");
                txtPassword.getStyleClass().add("invalid");
                txtUserName.requestFocus();
                txtUserName.selectAll();
            }else{
                String role = rst.getString("role");
                System.setProperty("currentLoggedUser", username);
                System.setProperty("currentLoggedUserRole", role);
                URL mainViewUrl = getClass().getResource("/view/MainScene.fxml");
                Scene mainViewScene = new Scene(FXMLLoader.load(mainViewUrl));
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setTitle("Inventory System");
                stage.setScene(mainViewScene);
                stage.setMaximized(true);
                stage.centerOnScreen();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    }


