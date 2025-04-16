package org.basvalk.ipwrcback.Controller;

import org.basvalk.ipwrcback.Model.UserModel;
import org.basvalk.ipwrcback.Repository.UserRepository;
import org.basvalk.ipwrcback.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "https://ipwrc25back.onrender.com/")
@CrossOrigin(origins = "https://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> creds) {
        String username = creds.get("username");
        String password = creds.get("password");

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            System.out.println("Password validated for user: " + username);
        } catch (BadCredentialsException e) {
            System.out.println(" Invalid password for user: " + username);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        UserModel user = userRepo.findByUsername(username).orElseThrow();
        String token = jwtUtil.generateToken(username, user.getRole());

        return Map.of("token", token);
    }



}
