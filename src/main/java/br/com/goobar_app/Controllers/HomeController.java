package br.com.goobar_app.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ws")
public class HomeController {

    @GetMapping ("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Autorização de dono realizada com sucesso");
    }
}
