package com.busguidance;

public class Security {

    public boolean validatePassword(String username, String password) {
        System.out.println("Security validates credentials.");

        return username.equals("passenger01")
                && password.equals("password123");
    }
}
