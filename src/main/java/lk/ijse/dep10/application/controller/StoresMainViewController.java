package lk.ijse.dep10.application.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StoresMainViewController {

    @FXML
    private Label lblProduction;

    @FXML
    private Label lblPurchase;

    @FXML
    private Label lblStores;

    @FXML
    private BorderPane mainScene;

    private Pane subScene;

    public void initialize(){
        try {
            lblStoresOnMouseClicked(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void lblProductionOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void lblPurchaseOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void lblStoresOnMouseClicked(MouseEvent event) throws IOException {
         subScene = FXMLLoader.load(getClass().getResource("/view/StoresView.fxml"));
         mainScene.setCenter(subScene);


    }

}
