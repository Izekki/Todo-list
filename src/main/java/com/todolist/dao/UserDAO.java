package com.todolist.dao;

import com.todolist.model.User;
import com.todolist.util.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User login(String rfc, String password) {
        String sql = "SELECT * FROM users WHERE rfc = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rfc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (PasswordHasher.check(password, rs.getString("password_hash"))) {
                    return new User(rs.getInt("id"), rs.getString("nombre"),
                            rs.getString("apellido_paterno"), rs.getString("apellido_materno"),
                            rs.getDate("fecha_nacimiento").toLocalDate(), rs.getString("rfc"),
                            rs.getString("password_hash"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean register(User user) {
        String sql = "INSERT INTO users (nombre, apellido_paterno, apellido_materno, fecha_nacimiento, rfc, password_hash) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellidoPaterno());
            ps.setString(3, user.getApellidoMaterno());
            ps.setDate(4, java.sql.Date.valueOf(user.getFechaNacimiento()));
            ps.setString(5, user.getRfc());
            ps.setString(6, user.getPasswordHash());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPasswordHash);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
