package lk.ijse.dep10.application.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainSceneController {

    public Label lblDate;
    public Label lblTime;
    public Label lblCurrentUser;
    public Label lblPurchase;
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

    public void initialize() {
        lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        KeyFrame time = new KeyFrame(Duration.seconds(1), event -> {
            lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
            lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        });
        Timeline timeline = new Timeline(time);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();

        lblCurrentUser.setText(System.getProperty("currentLoggedUser"));
        String currentLoggedUserRole = System.getProperty("currentLoggedUserRole");
        if (currentLoggedUserRole.equals("USER")){
            lblUser.setVisible(false);
        }else lblUser.setVisible(true);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene(new FXMLLoader(getClass().getResource("/view/LoginView.fxml")).load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    @FXML
    void lblItemsOnMouseClicked(MouseEvent event) throws IOException {
        subScene = new FXMLLoader().load(getClass().getResource("/view/ItemDetail.fxml"));
        mainPain.setCenter(subScene);
    }

    @FXML
    void lblProductionOnMouseClicked(MouseEvent event) throws IOException {
        subScene = new FXMLLoader().load(getClass().getResource("/view/Production.fxml"));
        mainPain.setCenter(subScene);
    }

    @FXML
    void lblStoresOnMouseClicked(MouseEvent event) throws IOException {
    }

    @FXML
    void lblSupplierOnMouseClicked(MouseEvent event) throws IOException {
        subScene = new FXMLLoader().load(getClass().getResource("/view/SupplierScene.fxml"));
        mainPain.setCenter(subScene);
    }

    @FXML
    void lblUserOnMouseClicked(MouseEvent event) throws IOException {
        subScene = new FXMLLoader().load(getClass().getResource("/view/NewUserView.fxml"));
        mainPain.setCenter(subScene);

//        Stage stage = new Stage();
//        stage.setScene(new Scene(FXMLLoader.load(
//                getClass().getResource("/view/NewUserView.fxml"))));
//        stage.show();
//        stage.centerOnScreen();
//        stage.setResizable(false);
    }

}
