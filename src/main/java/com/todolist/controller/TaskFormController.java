package com.todolist.controller;

import com.todolist.dao.TaskDAO;
import com.todolist.model.Task;
import com.todolist.util.SessionManager;
import com.todolist.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskFormController {
    @FXML private TextField txtTitulo;
    @FXML private TextArea txtDescripcion;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<String> cbHora;
    @FXML private ComboBox<String> cbMinutos;


    private final TaskDAO taskDAO = new TaskDAO();

    @FXML
    public void initialize() {
        Validator.addTextLimiter(txtTitulo, 100);

        for (int i = 0; i < 24; i++) {
            cbHora.getItems().add(String.format("%02d", i));
        }

        for (int i = 0; i < 60; i += 5) {
            cbMinutos.getItems().add(String.format("%02d", i));
        }

        cbHora.getSelectionModel().select("12");
        cbMinutos.getSelectionModel().select("00");

    }

    @FXML
    public void onSave() {
        // Validaciones
        if (txtTitulo.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Campo Obligatorio", "El título es necesario.");
            return;
        }

        if (dpFecha.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Fecha Inválida", "Debes seleccionar una fecha.");
            return;
        }

        LocalDate fecha = dpFecha.getValue();
        int hora = Integer.parseInt(cbHora.getValue());
        int minuto = Integer.parseInt(cbMinutos.getValue());
        LocalDateTime fechaHoraCompleta = LocalDateTime.of(fecha, LocalTime.of(hora,minuto));


        Task newTask = new Task();
        newTask.setUserId(SessionManager.getCurrentUser().getId());
        newTask.setTitulo(txtTitulo.getText().trim());
        newTask.setDescripcion(txtDescripcion.getText().trim());
        newTask.setFechaRealizacion(fechaHoraCompleta);
        newTask.setEstado("PENDIENTE");

        if (taskDAO.insertTask(newTask)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Tarea guardada correctamente.");
            limpiarCampos();

            try {
                com.todolist.Main.setRoot("DashboardView", "Mi Lista de Tareas");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error de Navegación", "No se pudo regresar al Dashboard.");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo guardar en el sistema.");
        }
    }

    @FXML
    public void onCancel() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtDescripcion.clear();
        dpFecha.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}