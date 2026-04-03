package com.todolist.controller;

import com.todolist.dao.TaskDAO;
import com.todolist.model.Task;
import com.todolist.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class DashboardController {
    @FXML private Label lblBienvenida;
    @FXML private TableView<Task> tblPending, tblCompleted;
    @FXML private TableColumn<Task, String> colTitleP, colTitleC;
    @FXML private TableColumn<Task, LocalDateTime> colDateP, colDateC;
    @FXML private TableColumn<Task, Void> colActionP, colActionC;

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

        setupActionColumns();
        loadData();

    }

    private void setupActionColumns() {
        colActionP.setCellFactory(param -> new TableCell<>() {
            private final Button btnDone = new Button("✔ TERMINAR");
            private final Button btnView = new Button("👁 VER");
            private final Button btnEdit = new Button("✏ EDITAR");
            private final Button btnDelete = new Button("🗑 ELIMINAR");
            private final HBox pane = new HBox(5, btnDone, btnView, btnEdit, btnDelete);

            {
                btnDone.setTooltip(new Tooltip("Marcar como Terminada"));
                btnView.setTooltip(new Tooltip("Ver Detalle"));
                btnEdit.setTooltip(new Tooltip("Editar Tarea"));
                btnDelete.setTooltip(new Tooltip("Eliminar Tarea"));
                
                btnDone.getStyleClass().add("button-success");
                btnView.getStyleClass().add("button-view");
                btnEdit.getStyleClass().add("button-edit");
                btnDelete.getStyleClass().add("button-danger");

                btnDone.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    DashboardController.this.onCompleteTask(task);
                });

                btnView.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    onViewDetails(task);
                });
                btnEdit.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    onEditTask(task);
                });
                btnDelete.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    onDeleteTask(task);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        colActionC.setCellFactory(param -> new TableCell<>() {
            private final Button btnRestore = new Button("↩ RESTAURAR");
            private final Button btnView = new Button("👁 VER");
            private final Button btnDelete = new Button("🗑 ELIMINAR");
            private final HBox pane = new HBox(5, btnRestore, btnView, btnDelete);

            {
                btnRestore.setTooltip(new Tooltip("Restaurar a Pendiente"));
                btnView.setTooltip(new Tooltip("Ver Detalle"));
                btnDelete.setTooltip(new Tooltip("Eliminar Tarea"));

                btnRestore.getStyleClass().add("button-restore");
                btnView.getStyleClass().add("button-view");
                btnDelete.getStyleClass().add("button-danger");

                btnRestore.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    DashboardController.this.onRestoreTask(task);
                });

                btnView.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    onViewDetails(task);
                });
                btnDelete.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    onDeleteTask(task);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
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

    private void onDeleteTask(Task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de eliminar: " + task.getTitulo() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            if (taskDAO.deleteTask(task.getId())) {
                loadData();
            }
        }
    }

    private void onViewDetails(Task task) {
        try {
            FXMLLoader loader = com.todolist.Main.setRoot("TaskDetailView", "Detalles de Tarea");
            TaskDetailController controller = loader.getController();
            controller.setTask(task);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void onEditTask(Task task) {
        try {
            FXMLLoader loader = com.todolist.Main.setRoot("TaskFormView", "Editar Tarea");
            TaskFormController controller = loader.getController();
            controller.setTaskToEdit(task);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    public void onViewDetails() {
        Task selected = tblPending.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tblCompleted.getSelectionModel().getSelectedItem();
        if (selected != null) onViewDetails(selected);
    }

    @FXML
    public void onDeleteTask() {
        Task selected = tblPending.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tblCompleted.getSelectionModel().getSelectedItem();
        if (selected != null) onDeleteTask(selected);
    }

    @FXML
    public void onEditTask() {
        Task selected = tblPending.getSelectionModel().getSelectedItem();
        if (selected != null) onEditTask(selected);
    }

    private void onCompleteTask(Task task) {
        if (taskDAO.updateTaskStatus(task.getId(), "TERMINADA")) {
            loadData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tarea Terminada");
            alert.setHeaderText(null);
            alert.setContentText("La tarea '" + task.getTitulo() + "' ha sido marcada como terminada.");
            alert.showAndWait();
        }
    }

    private void onRestoreTask(Task task) {
        if (taskDAO.updateTaskStatus(task.getId(), "PENDIENTE")) {
            loadData();
        }
    }

    @FXML
    public void onChangePassword() {
        try {
            com.todolist.Main.setRoot("ChangePasswordView", "Cambiar Contraseña");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}