package com.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("ToDoList Login");
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}