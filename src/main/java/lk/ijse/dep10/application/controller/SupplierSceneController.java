//<<<<<<< HEAD
//package lk.ijse.dep10.application.controller;
//
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//import lk.ijse.dep10.application.db.DBConnection;
//import lk.ijse.dep10.application.model.Company;
//
//import java.io.IOException;
//import java.sql.*;
//
//public class SupplierSceneController {
//
//    public TableView<Company> tblDetails;
//    public Button btnAddNewSupplier;
//    public TextField txtRefContact;
//    public TextField txtCompanyId;
//    public TextField txtRefName;
//    public TextField txtContactNo;
//    public TextField txtCompanyName;
//    public TextField txtAddress;
//    @FXML
//    private Button btnDelete;
//
//    @FXML
//    private Button btnSave;
//
//    public void initialize() {
//        btnDelete.setDisable(true);
//
//        tblDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("companyId"));
//        tblDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("companyName"));
//        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("companyContact"));
//        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
//        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repName"));
//        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repContact"));
//
//        loadAllCompanies();
//
//        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov,previous,current)->{
//            btnDelete.setDisable(current==null);
//            if (current==null) return;
//            txtAddress.setText(current.getCompanyAddress());
//            txtCompanyId.setText(current.getCompanyId());
//            txtCompanyName.setText(current.getCompanyName());
//            txtContactNo.setText(current.getCompanyContact());
//            txtRefName.setText(current.getRepName());
//            txtRefContact.setText(current.getRepContact());
//        });
//    }
//
//    private void loadAllCompanies() {
//
//    }
//
//    @FXML
//    void btnDeleteOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void btnSaveOnAction(ActionEvent event) {
//
//    }
//
////    private boolean isDataValid() {
////
////    }
//
//    public void btnAddNewSupplierOnAction(ActionEvent actionEvent) {
//        txtCompanyId.setText(generateId());
//        txtCompanyName.clear();
//        txtContactNo.clear();
//        txtAddress.clear();
//        txtRefName.clear();
//        txtRefContact.clear();
//        tblDetails.getSelectionModel().clearSelection();
//        txtCompanyName.requestFocus();
//    }
//
//    private String generateId() {
//        ObservableList<Company> companyList = tblDetails.getItems();
//        if (companyList.isEmpty()) {
//            return "C001";
//        } else {
//            String companyId = companyList.get(companyList.size() - 1).getCompanyId();
//            String oldId = companyId.substring(1);
//            return String.format("C%03d",Integer.parseInt(oldId)+1);
//        }
//    }
//
//
//}
//=======
////package lk.ijse.dep10.application.controller;
////
////import javafx.application.Platform;
////import javafx.collections.ObservableList;
////import javafx.event.ActionEvent;
////import javafx.fxml.FXML;
////import javafx.fxml.FXMLLoader;
////import javafx.scene.Node;
////import javafx.scene.Scene;
////import javafx.scene.control.Alert;
////import javafx.scene.control.Button;
////import javafx.scene.control.TableView;
////import javafx.scene.control.TextField;
////import javafx.scene.control.cell.PropertyValueFactory;
////import javafx.stage.Stage;
////import lk.ijse.dep10.application.db.DBConnection;
////import lk.ijse.dep10.application.model.Company;
////
////import java.io.IOException;
////import java.sql.*;
////
////public class SupplierSceneController {
////
////    public TableView<Company> tblDetails;
////    public Button btnAddNewSupplier;
////    public TextField txtRefContact;
////    public TextField txtCompanyId;
////    public TextField txtRefName;
////    public TextField txtContactNo;
////    public TextField txtCompanyName;
////    public TextField txtAddress;
////    @FXML
////    private Button btnDelete;
////
////    @FXML
////    private Button btnSave;
////
////    public void initialize() {
////        btnDelete.setDisable(true);
////
////        tblDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("companyId"));
////        tblDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("companyName"));
////        tblDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("companyContact"));
////        tblDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
////        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repName"));
////        tblDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("repContact"));
////
////        loadAllCompanies();
////
////        tblDetails.getSelectionModel().selectedItemProperty().addListener((ov,previous,current)->{
////            btnDelete.setDisable(current==null);
////            if (current==null) return;
////            txtAddress.setText(current.getCompanyAddress());
////            txtCompanyId.setText(current.getCompanyId());
////            txtCompanyName.setText(current.getCompanyName());
////            txtContactNo.setText(current.getCompanyContact());
////            txtRefName.setText(current.getRepName());
////            txtRefContact.setText(current.getRepContact());
////        });
////    }
////
////    private void loadAllCompanies() {
////        try {
////            Connection connection = DBConnection.getInstance().getConnection();
////            Statement stm = connection.createStatement();
////            ResultSet rst = stm.executeQuery("SELECT * FROM Company_Details");
////            ObservableList<Company> companyList = tblDetails.getItems();
////
////            while (rst.next()) {
////                String companyId = rst.getString("company_id");
////                String companyName = rst.getString("company_name");
////                String companyContact = rst.getString("company_contact");
////                String companyAddress = rst.getString("company_address");
////                String repName = rst.getString("rep_name");
////                String repContact = rst.getString("rep_contact");
////                companyList.add(new Company(companyId,companyName,companyContact,companyAddress,repName,repContact));
////            }
////            Platform.runLater(btnAddNewSupplier::fire);
////        } catch (SQLException e) {
////            e.printStackTrace();
////            new Alert(Alert.AlertType.ERROR,"Failed to load companies details, try again!").show();
////            Platform.exit();
////        }
////    }
////
////    @FXML
////    void btnDeleteOnAction(ActionEvent event) {
////        try{
////            Connection connection = DBConnection.getInstance().getConnection();
////            Statement stm = connection.createStatement();
////            String sql = "DELETE FROM Company_Details WHERE company_id=%s";
////            sql = String.format(sql, tblDetails.getSelectionModel().getSelectedItem().getCompanyId());
////            stm.executeUpdate(sql);
////
////            tblDetails.getItems().remove(tblDetails.getSelectionModel().getSelectedItem());
////            if (tblDetails.getItems().isEmpty()) btnAddNewSupplier.fire();
////        } catch (SQLException e) {
////            e.printStackTrace();
////            new Alert(Alert.AlertType.ERROR, "Failed to delete the company, try again!").show();
////        }
////    }
////
////    @FXML
////    void btnSaveOnAction(ActionEvent event) {
////        if (!isDataValid()) return;
////        try {
////            Company company = new Company(txtCompanyId.getText(),
////                    txtCompanyName.getText(),
////                    txtContactNo.getText(),
////                    txtAddress.getText(),
////                    txtRefName.getText(),
////                    txtRefContact.getText());
////
////            Connection connection = DBConnection.getInstance().getConnection();
////            PreparedStatement stm = connection.prepareStatement("INSERT INTO Company_Details VALUES (?,?,?,?,?,?)");
////
////            Company selectedCompany = tblDetails.getSelectionModel().getSelectedItem();
////
////            if (selectedCompany == null) {
////                stm.setString(1,company.getCompanyId());
////                stm.setString(2,company.getCompanyName());
////                stm.setString(3,company.getCompanyContact());
////                stm.setString(4,company.getCompanyAddress());
////                stm.setString(5,company.getRepName());
////                stm.setString(6,company.getRepContact());
////                stm.executeUpdate();
////                tblDetails.getItems().add(company);
////            } else {
////                connection.prepareStatement("UPDATE Company_Details SET company_name=?,company_contact=?,company_address=?,rep_name=?,rep_contact=? WHERE company_id=?");
////
////                stm.setString(1,company.getCompanyName());
////                stm.setString(2,company.getCompanyContact());
////                stm.setString(3,company.getCompanyAddress());
////                stm.setString(4,company.getRepName());
////                stm.setString(5,company.getRepContact());
////                stm.setString(6,selectedCompany.getCompanyId());
////                stm.executeUpdate();
////                ObservableList<Company> companyList = tblDetails.getItems();
////                int selectedCompanyIndex = companyList.indexOf(selectedCompany);
////                companyList.set(selectedCompanyIndex, company);
////                tblDetails.refresh();
////            }
////
////            btnAddNewSupplier.fire();
////
////            connection.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////            new Alert(Alert.AlertType.ERROR, "Failed to save the company, try again!").show();
////        }
////    }
////
////    private boolean isDataValid() {
////        boolean dataValid = true;
////        for (Node node : new Node[]{txtCompanyName, txtContactNo, txtRefName, txtRefContact}) {
////            node.getStyleClass().remove("invalid");
////        }
////
////        String companyName = txtCompanyName.getText();
////        String companyContact = txtContactNo.getText();
////        String repName = txtRefName.getText();
////        String repContact = txtRefContact.getText();
////
////        if (!companyName.matches("[A-Za-z0-9]{1,}")) {
////            txtCompanyName.requestFocus();
////            txtCompanyName.selectAll();
////            txtCompanyName.getStyleClass().add("invalid");
////            dataValid = false;
////        }
////
////        if (!companyContact.matches("\\d{3}-\\d{7}")) {
////            txtContactNo.requestFocus();
////            txtContactNo.selectAll();
////            txtContactNo.getStyleClass().add("invalid");
////            dataValid = false;
////        }
////
////        if (!repName.matches("[A-Za-z0-9]{1,}")) {
////            txtRefName.requestFocus();
////            txtRefName.selectAll();
////            txtRefName.getStyleClass().add("invalid");
////            dataValid = false;
////        }
////
////        if (!repContact.matches("\\d{3}-\\d{7}")) {
////            txtRefContact.requestFocus();
////            txtRefContact.selectAll();
////            txtRefContact.getStyleClass().add("invalid");
////            dataValid = false;
////        }
////        return dataValid;
////    }
////
////    public void btnAddNewSupplierOnAction(ActionEvent actionEvent) {
////        txtCompanyId.setText(generateId());
////        txtCompanyName.clear();
////        txtContactNo.clear();
////        txtAddress.clear();
////        txtRefName.clear();
////        txtRefContact.clear();
////        tblDetails.getSelectionModel().clearSelection();
////        txtCompanyName.requestFocus();
////    }
////
////    private String generateId() {
////        ObservableList<Company> companyList = tblDetails.getItems();
////        if (companyList.isEmpty()) {
////            return "C001";
////        } else {
////            String companyId = companyList.get(companyList.size() - 1).getCompanyId();
////            String oldId = companyId.substring(1);
////            return String.format("C%03d",Integer.parseInt(oldId)+1);
////        }
////    }
////
////
////}
//>>>>>>> refs/remotes/origin/main
