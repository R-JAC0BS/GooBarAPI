package br.com.goobar_app.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('CNPJ')")
@RequestMapping("ws")
public class HomeController {

    @GetMapping ("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Autorização de dono realizada com sucesso");
    }
}
