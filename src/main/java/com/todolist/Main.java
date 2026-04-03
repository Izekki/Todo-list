package com.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        setRoot("LoginView","Iniciar Sesión");
    }

    public static FXMLLoader setRoot(String fxml, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        Scene scene = new Scene(loader.load());
        String css = Main.class.getResource("/css/tech-dark.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        return loader;
    }

    public static void main(String[] args) { launch(); }
}