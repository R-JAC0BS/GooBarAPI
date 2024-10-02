package br.com.goobar_app.Controllers;


import br.com.goobar_app.Config.JwtUtils;
import br.com.goobar_app.DTOS.LoginDTOS;
import br.com.goobar_app.DTOS.RegisterDto;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.service.BarService;
import br.com.goobar_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class UserController {
    private static final UserModel userModel = new UserModel();
    @Autowired
    private UserService userService;
    @Autowired
    private final AuthenticationConfiguration auth;
    private BarService barService;

    @Autowired
    public UserController(AuthenticationConfiguration auth) {
        this.auth = auth;
    }


    @PostMapping ("/register")
    public ResponseEntity<UserModel> register(@Valid @RequestBody RegisterDto registerDto) throws Exception {
        BeanUtils.copyProperties(registerDto, userModel);
        userService.saveAcount(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @PostMapping ("/login")
    public ResponseEntity <String> login ( @RequestBody LoginDTOS loginDto) throws Exception {
        auth.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password()));
        String jwtToken = JwtUtils.generateTokenFromUsername(loginDto.email());
        return ResponseEntity.ok(jwtToken);
    }

    @DeleteMapping("DeleteUser")
    public ResponseEntity <String> DeleteUser () throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Extrair Email do SecurityContextHolder
        Optional<UserModel> optionalUserModel = (Optional<UserModel>) authentication.getPrincipal();
        //DownCast pra filtrar apenas o endereço de email e converter em String
        String Email = optionalUserModel.map(UserModel::getEmail).orElse("");
        userService.DeleteAcount(Email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Deleted");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cleanMessage);
    }



}
