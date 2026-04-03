package com.todolist.controller;

import com.todolist.dao.UserDAO;
import com.todolist.model.User;
import com.todolist.util.PasswordHasher;
import com.todolist.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class ChangePasswordController {

    @FXML
    private PasswordField txtCurrentPassword;
    @FXML
    private PasswordField txtNewPassword;
    @FXML
    private PasswordField txtConfirmPassword;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void onSave() {
        String currentInput = txtCurrentPassword.getText();
        String newPassword = txtNewPassword.getText();
        String confirmation = txtConfirmPassword.getText();

        User currentUser = SessionManager.getCurrentUser();

        if (!PasswordHasher.check(currentInput, currentUser.getPasswordHash())) {
            showAlert(Alert.AlertType.ERROR, "Error de Identidad", "La contraseña actual es incorrecta.");
            return;
        }

        if (currentInput.equals(newPassword)) {
            showAlert(Alert.AlertType.WARNING, "Contraseña Repetida",
                    "La nueva contraseña debe ser diferente a la actual.");
            return;
        }

        if (newPassword.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Seguridad Insuficiente",
                    "La contraseña debe tener al menos 3 caracteres.");
            return;
        }

        if (!newPassword.equals(confirmation)) {
            showAlert(Alert.AlertType.ERROR, "Error de Confirmación",
                    "La confirmación no coincide con la nueva contraseña.");
            return;
        }

        String newHash = PasswordHasher.hash(newPassword);
        if (userDAO.updatePassword(currentUser.getId(), newHash)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Tu contraseña ha sido actualizada correctamente.");
            currentUser.setPasswordHash(newHash);
            regresarAlDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error FATAL", "No se pudo actualizar la contraseña en el servidor.");
        }
    }

    @FXML
    public void onCancel() {
        regresarAlDashboard();
    }

    private void regresarAlDashboard() {
        try {
            com.todolist.Main.setRoot("DashboardView", "Mi Lista de Tareas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
