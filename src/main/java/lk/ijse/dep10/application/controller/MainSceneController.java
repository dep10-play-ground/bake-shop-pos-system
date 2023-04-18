package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController {

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblItems;

    @FXML
    private Label lblProduction;

    @FXML
    private Label lblStores;

    @FXML
    private Label lblSupplier;

    @FXML
    private Label lblUser;

    @FXML
    private BorderPane mainPain;

    private Pane subScene;

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene(new FXMLLoader(getClass().getResource("/view/LoginView.fxml")).load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    @FXML
    void lblItemsOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void lblProductionOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void lblStoresOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void lblSupplierOnMouseClicked(MouseEvent event) throws IOException {
        subScene = new FXMLLoader().load(getClass().getResource("/view/SupplierScene.fxml"));
        mainPain.setCenter(subScene);
    }

    @FXML
    void lblUserOnMouseClicked(MouseEvent event) {

    }

}
