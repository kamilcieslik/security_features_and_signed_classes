package com.github.kamilcieslik.academic.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            primaryStage.setTitle("Text File Encrypter Decrypter");
            primaryStage.getIcons().add(new Image("/image/app_icon.png"));
            primaryStage.setMinWidth(970);
            primaryStage.setMinHeight(600);
            primaryStage.setScene(new Scene(parent, 1600, 900));
            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
