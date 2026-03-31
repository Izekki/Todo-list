package com.todolist.util;
import com.todolist.model.User;

public class SessionManager {
    private static User currentUser;
    public static void setSession(User user) { currentUser = user; }
    public static User getCurrentUser() { return currentUser; }
    public static void cleanSession() { currentUser = null; }
}