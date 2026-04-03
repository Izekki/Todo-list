package com.todolist.controller;

import com.todolist.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TaskDetailController {
    @FXML private Text txtTitulo, txtDescripcion;
    @FXML private Label lblFecha, lblEstado;

    private Task currentTask;

    public void setTask(Task task) {
        this.currentTask = task;
        txtTitulo.setText(task.getTitulo());

        if (task.getDescripcion() == null || task.getDescripcion().isEmpty()) {
            txtDescripcion.setText("Sin descripción adicional.");
        } else {
            txtDescripcion.setText(task.getDescripcion());
        }

        lblFecha.setText(task.getFechaRealizacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a")));
        lblEstado.setText(task.getEstado());
    }

    @FXML
    public void onClose() {
        try {
            com.todolist.Main.setRoot("DashboardView", "Mi Lista de Tareas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onEdit() {
        try {
            FXMLLoader loader = com.todolist.Main.setRoot("TaskFormView", "Editar Tarea");
            TaskFormController controller = loader.getController();

            controller.setTaskToEdit(currentTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}