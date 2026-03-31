package com.todolist.controller;

import com.todolist.dao.UserDAO;
import com.todolist.model.User;
import com.todolist.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    // Estos IDs deben coincidir con el fx:id del LoginView.fxml
    @FXML
    private TextField txtRfc;
    @FXML private PasswordField txtPassword;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void onLogin() {
        String rfc = txtRfc.getText().trim();
        String password = txtPassword.getText();

        // 1. Validación simple de campos vacíos
        if (rfc.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, llena todos los campos.");
            return;
        }

        // 2. Intentar login mediante el DAO (Consultando la tabla 'users')
        User user = userDAO.login(rfc, password);

        if (user != null) {
            // 3. Guardar usuario en la sesión
            SessionManager.setSession(user);

            System.out.println("¡Login exitoso! Bienvenido: " + user.getNombre());

            // TODO: DashboardView.fxml más adelante
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Bienvenido, " + user.getNombre());
        } else {
            // 4. Error de credenciales
            showAlert(Alert.AlertType.ERROR, "Error de acceso", "RFC o contraseña incorrectos.");
        }
    }

    @FXML
    public void onGoToRegister() {
        System.out.println("Navegando a la vista de Registro...");
    }

    // Utilidad rápida para mostrar alertas en JavaFX
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
