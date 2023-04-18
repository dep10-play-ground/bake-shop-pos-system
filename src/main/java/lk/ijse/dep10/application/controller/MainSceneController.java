package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class MainSceneController {

    public AnchorPane root;
    public Label lblUserName;
    @FXML
        private Button btnLogOut;

        @FXML
        private Label lblDate;

        @FXML
        private Label lblItem;

        @FXML
        private Label lblProduction;

        @FXML
        private Label lblStores;

        @FXML
        private Label lblSupplier;

        @FXML
        private Label lblTime;

        @FXML
        private Label lblUser;

        @FXML
        private AnchorPane mainScene;
        Stage newStage;

        @FXML
        void btnLogOutOnAction(ActionEvent event) {

        }

        @FXML
        void lblItemOnMouseClicked(MouseEvent event) {

        }

        @FXML
        void lblProductionOnMouseClicked(MouseEvent event) {

        }

        @FXML
        void lblStoresOnMouseClicked(MouseEvent event) {

        }

        @FXML
        void lblSupplierOnMouseClicked(MouseEvent event) throws IOException {
        }

        @FXML
        void lblUserOnMouseClicked(MouseEvent event) {

        }
    }

