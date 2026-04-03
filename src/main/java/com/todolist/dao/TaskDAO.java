package com.todolist.dao;

import com.todolist.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TaskDAO {

    public boolean insertTask(Task task) {
        String sql = "INSERT INTO tasks (user_id, titulo, descripcion, fecha_realizacion, estado) VALUES (?, ?, ?, ?, 'PENDIENTE')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, task.getUserId());
            ps.setString(2, task.getTitulo());
            ps.setString(3, task.getDescripcion());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(task.getFechaRealizacion()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Task> getTasksByUser(int userId, String estado) {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND estado = ? ORDER BY fecha_realizacion ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_realizacion").toLocalDateTime(),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET titulo = ?, descripcion = ?, fecha_realizacion = ?, estado = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, task.getTitulo());
            ps.setString(2, task.getDescripcion());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(task.getFechaRealizacion()));
            ps.setString(4, task.getEstado());
            ps.setInt(5, task.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTaskStatus(int taskId, String status) {
        String sql = "UPDATE tasks SET estado = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, taskId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}