package org.basvalk.ipwrcback.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CorsPreflightController {

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/**")
    public ResponseEntity<?> handlePreflight() {
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "https://ipwrc25-hdfc.onrender.com")
                .header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS")
                .header("Access-Control-Allow-Headers", "Authorization,Content-Type,Accept,Origin")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }
}
