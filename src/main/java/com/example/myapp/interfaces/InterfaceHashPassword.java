package com.example.myapp.interfaces;

public interface InterfaceHashPassword {
    public String createPassword(String password);

    public boolean comparePassword(String password, String hashPassword);

    public boolean changePassword(String lastPassword, String newPassword);
}
