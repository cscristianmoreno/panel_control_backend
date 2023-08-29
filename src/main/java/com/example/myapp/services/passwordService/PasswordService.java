package com.example.myapp.services.passwordService;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.myapp.interfaces.InterfaceHashPassword;

@Service
public class PasswordService implements InterfaceHashPassword {

    @Override
    public String createPassword(String password) {
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        return hashPassword;
    }

    @Override
    public boolean comparePassword(String password, String hashPassword) {
        boolean compare = BCrypt.checkpw(password, hashPassword);
        return compare;
    }
    
    @Override
    public boolean changePassword(String password, String hashPassword) {
        return false;
    }
}
