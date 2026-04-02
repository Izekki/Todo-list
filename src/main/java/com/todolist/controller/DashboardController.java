package com.todolist.controller;

import com.todolist.dao.TaskDAO;
import com.todolist.model.Task;
import com.todolist.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableCell;

public class DashboardController {
    @FXML private Label lblBienvenida;
    @FXML private TableView<Task> tblPending, tblCompleted;
    @FXML private TableColumn<Task, LocalDateTime> colTitleP, colTitleC;
    @FXML private TableColumn<Task, LocalDateTime> colDateP, colDateC;

    private final TaskDAO taskDAO = new TaskDAO();

    @FXML
    public void initialize() {

        lblBienvenida.setText("¡Bienvenido, " + SessionManager.getCurrentUser().getNombre() + "!");

        colTitleP.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDateP.setCellValueFactory(new PropertyValueFactory<>("fechaRealizacion"));
        colTitleC.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDateC.setCellValueFactory(new PropertyValueFactory<>("fechaRealizacion"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        colDateP.setCellFactory(column -> new TableCell<Task, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        colDateC.setCellFactory(column -> new TableCell<Task, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        loadData();

    }

    public void loadData() {
        int userId = SessionManager.getCurrentUser().getId();
        tblPending.setItems(taskDAO.getTasksByUser(userId, "PENDIENTE"));
        tblCompleted.setItems(taskDAO.getTasksByUser(userId, "TERMINADA"));
    }

    @FXML
    public void onAddTask() {
        try {
            com.todolist.Main.setRoot("TaskFormView", "Nueva Tarea");
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void onLogout() {
        SessionManager.cleanSession();
        try {
            com.todolist.Main.setRoot("LoginView", "Iniciar Sesión");
        } catch (Exception e) { e.printStackTrace(); }
    }
}