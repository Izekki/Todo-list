package com.todolist.controller;

import com.todolist.dao.UserDAO;
import com.todolist.model.User;
import com.todolist.util.SessionManager;
import com.todolist.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtRfc;
    @FXML private PasswordField txtPassword;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize(){
        Validator.addTextLimiter(txtRfc,13);
    }

    @FXML
    public void onLogin() {
        String rfc = txtRfc.getText().trim();
        String password = txtPassword.getText();

        if (rfc.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, llena todos los campos.");
            return;
        }

        User user = userDAO.login(rfc, password);

        if (user != null) {
            SessionManager.setSession(user);
            System.out.println("¡Login exitoso! Bienvenido: " + user.getNombre());

            try {
                com.todolist.Main.setRoot("DashboardView", "Mi Lista de Tareas - " + user.getNombre());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error de Sistema", "No se pudo cargar la vista principal.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de acceso", "RFC o contraseña incorrectos.");
        }
    }

    @FXML
    public void onGoToRegister() {
        try {
            com.todolist.Main.setRoot("RegisterView","Registro de Usuario");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
