package com.todolist.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    public static boolean check(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}