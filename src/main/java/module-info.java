module com.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    opens com.todolist to javafx.graphics;
    opens com.todolist.controller to javafx.fxml;
    opens com.todolist.model to javafx.base;
    exports com.todolist;
}