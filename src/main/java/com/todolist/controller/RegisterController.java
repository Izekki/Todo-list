package com.todolist.controller;

import com.todolist.dao.UserDAO;
import com.todolist.model.User;
import com.todolist.util.PasswordHasher;
import com.todolist.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class RegisterController {
    @FXML private TextField txtNombre, txtApPaterno, txtApMaterno, txtRfc;
    @FXML private DatePicker dpFechaNac;
    @FXML private PasswordField txtPassword;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        Validator.addTextLimiter(txtNombre, 50);
        Validator.addTextLimiter(txtApPaterno, 50);
        Validator.addTextLimiter(txtApMaterno, 50);
        Validator.addTextLimiter(txtRfc, 13);
    }

    @FXML
    public void onRegister() {
        if (isInvalid()) {
            showAlert("Error", "Todos los campos son obligatorios (excepto Apellido Materno).");
            return;
        }

        User newUser = new User(
                0,
                txtNombre.getText(),
                txtApPaterno.getText(),
                txtApMaterno.getText(),
                dpFechaNac.getValue(),
                txtRfc.getText(),
                PasswordHasher.hash(txtPassword.getText())
        );

        if (userDAO.register(newUser)) {
            showAlert("Éxito", "Usuario registrado correctamente.");
            onBackToLogin();
        } else {
            showAlert("Error", "No se pudo registrar. Verifica que el RFC no exista ya.");
        }
    }

    @FXML
    public void onBackToLogin() {
        try {
            com.todolist.Main.setRoot("LoginView", "Iniciar Sesión");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private boolean isInvalid() {
        String rfc = txtRfc.getText().trim();
        boolean camposVacios = txtNombre.getText().isEmpty() || txtApPaterno.getText().isEmpty() ||
                dpFechaNac.getValue() == null || txtRfc.getText().isEmpty() ||
                txtPassword.getText().isEmpty();

        boolean rfcLongitudInvalida = rfc.length() <12 || rfc.length() > 13;
        return camposVacios || rfcLongitudInvalida;
    }

    private void showAlert(String title, String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}