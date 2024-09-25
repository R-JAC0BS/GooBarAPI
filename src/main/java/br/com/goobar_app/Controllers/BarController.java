package br.com.goobar_app.Controllers;


import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.service.BarService;
import br.com.goobar_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Bar")
public class BarController {
    @Autowired
    public BarService barService;
    @Autowired
    public UserDetailsService userDetailsService;
    public UserModel userModel;
    @Autowired
    public UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registerBar")
    public ResponseEntity <String> registerBar(@RequestBody BarModel bar) {
      String email = bar.getEmail();
      barService.setUserBar(email, bar);
      return ResponseEntity.status(HttpStatus.CREATED).body(email);
    }

    @GetMapping ("/getUser")
    public List<UserModel> getUser() {
        return userRepository.findAll();
    }


}
