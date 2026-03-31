module com.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    // Abrimos los paquetes para que JavaFX pueda acceder a ellos
    opens com.todolist.controller to javafx.fxml;
    opens com.todolist.model to javafx.base; // Necesario para que las tablas lean tus objetos

    exports com.todolist;
}