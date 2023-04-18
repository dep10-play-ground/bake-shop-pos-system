package lk.ijse.dep10.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplierSceneController {

    public TableView tblDetails;
    public Button btnAddNewSupplier;
    public Button btnPrevious;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void btnAddNewSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnPreviousOnAction(ActionEvent actionEvent) throws IOException {
        Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("/view/MainScene.fxml")));
        Stage supplierStage=(Stage)btnPrevious.getScene().getWindow();
        supplierStage.setScene(mainScene);
        supplierStage.show();
    }
}
