package com.example.myapp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.httpExceptions.exists.Exists;
import com.example.myapp.httpExceptions.unauthorized.Unauthorized;
import com.example.myapp.models.Users;
import com.example.myapp.repository.UsersRepository;
import com.example.myapp.services.passwordService.PasswordService;
import com.example.myapp.services.tokenService.TokenService;
import com.example.myapp.services.tokenService.build.TokenBuild;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@CrossOrigin(origins="*")
@Controller
@ResponseBody
public class Controllers {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordService servicePassword;

    @Autowired  
    TokenService tokenService;

    @PostMapping("/auth") 
    public ResponseEntity<Claims> auth(@RequestHeader("Authorization") String token) throws Unauthorized {

        try {
            String auth = token.split(" ")[1];
            Claims claims = tokenService.decodeToken(auth);

            ResponseEntity<Claims> authorization = new ResponseEntity<Claims>(claims, HttpStatus.OK);
            return authorization;
        }
        catch (ArrayIndexOutOfBoundsException | JwtException e) {
            throw new Unauthorized();
        }
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        List<Users> users = usersRepository.findAllUsers();
        return users;
    }

    @DeleteMapping("/users")
    public ResponseEntity<Boolean> deleteUser(@RequestParam("userId") int id) {
        usersRepository.deleteById(id);
        ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK); 
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Map<String, String> params) throws Exists {
        String email = params.get("email");
        String pw = params.get("password");

        String password = servicePassword.createPassword(pw);
        
        List<Users> check = usersRepository.findByEmail(email);

        if (check.size() > 0) {
            throw new Exists();
        }

        Users users = new Users();
        
        users.setName(params.get("name"));
        users.setLastname(params.get("lastname"));
        users.setEmail(params.get("email"));
        users.setPassword(password);
        users.setAge(Integer.parseInt(params.get("age")));

        usersRepository.save(users);
        
        ResponseEntity<Users> response = new ResponseEntity<Users>(users, HttpStatus.CREATED);
        return response;
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenBuild> login(@RequestBody Map<String, String> params) throws Unauthorized {
        String email = params.get("email");
        List<Users> user = usersRepository.findByEmail(email);
        
        if (user.size() == 0) {
            throw new Unauthorized();
        }

        String hashPassword = user.get(0).getPassword();
        String password = params.get("password");

        if (!servicePassword.comparePassword(password, hashPassword)) {
            throw new Unauthorized();
        }

        ResponseEntity<TokenBuild> response;

        Integer userId = user.get(0).getId();
        String em = user.get(0).getEmail();
        String name = user.get(0).getName();
        String lastname = user.get(0).getLastname();
        String lastsession = user.get(0).getLastsession();

        TokenBuild token = tokenService.createToken(userId.toString(), em, name, lastname, lastsession);

        response = new ResponseEntity<TokenBuild>(token, HttpStatus.ACCEPTED);
        return response;
    }
}
