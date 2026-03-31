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
}
