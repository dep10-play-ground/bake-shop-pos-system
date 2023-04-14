package lk.ijse.dep10.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.dep10.application.db.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (DBConnection.getInstance().getConnection() != null &&
                        !DBConnection.getInstance().getConnection().isClosed()) {
                    System.out.println("Database connection is about to close");
                    DBConnection.getInstance().getConnection().close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        generateSchemaIfNotExist();
        primaryStage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/LoginView.fxml")).load()));
        primaryStage.setTitle("Login Form");
        primaryStage.centerOnScreen();
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    private void generateSchemaIfNotExist() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SHOW TABLES");

            HashSet<String> tableNameSet = new HashSet<>();
            while (rst.next()){
                tableNameSet.add(rst.getString(1));
            }

            boolean tableExists = tableNameSet.
                    containsAll(Set.of("Employee"));

            if (!tableExists){
                System.out.println("Schema is about to auto generate");
                stm.execute(readSchemaScript());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }private String readSchemaScript(){
        InputStream is = getClass().getResourceAsStream("/schema.sql");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            StringBuilder dbScriptBuilder = new StringBuilder();
            while ((line = br.readLine()) != null){
                dbScriptBuilder.append(line);
            }
            return dbScriptBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
