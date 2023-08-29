package com.example.myapp.httpExceptions.exists;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class Exists extends Exception {
    
}
