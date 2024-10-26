package br.com.goobar_app.Controllers;


import br.com.goobar_app.Config.JwtUtils;
import br.com.goobar_app.DTOS.AlterUser;
import br.com.goobar_app.DTOS.LoginDTOS;
import br.com.goobar_app.DTOS.RegisterDto;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.ExtractEmail;
import br.com.goobar_app.service.BarService;
import br.com.goobar_app.service.ImageService;
import br.com.goobar_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("auth")
public class UserController {
    private static final UserModel userModel = new UserModel();
    @Autowired
    private UserService userService;
    @Autowired
    private final AuthenticationConfiguration auth;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final ImageService imageService;

    @Autowired
    public UserController(AuthenticationConfiguration auth, ImageService imageService) {
        this.auth = auth;
        this.imageService = imageService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@Valid @RequestBody RegisterDto registerDto) throws Exception {
        userModel.setId(null);
        BeanUtils.copyProperties(registerDto, userModel);

        userService.saveAcount(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);

    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTOS loginDto) throws Exception {

        auth.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));

        String jwtToken = JwtUtils.generateTokenFromUsername(loginDto.email());

        return ResponseEntity.ok(jwtToken);
    }
    /*
        Deleta seu propio Usuario não tem como passar path via url ele apenas deleta o ususario que ja esta authenticado na sessão
     */
    @DeleteMapping("DeleteMyUser")
    public ResponseEntity<String> DeleteUser() throws Exception {
        userService.DeleteAcount(ExtractEmail.extrairEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Deleted");
    }


    /*
    Rota endpoint FindUser retorna o seu propio usuario filtrando pelo email extraido do authenticate
     */

    @GetMapping("FindUser")
    public ResponseEntity<Optional<UserModel>> FindUser(RegisterDto registerDto) throws Exception {
        Optional<UserModel> user = userRepository.findByEmail(ExtractEmail.extrairEmail());
        BeanUtils.copyProperties(user, registerDto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("allusers")
    public List <UserModel> FindAllUsers() throws Exception {
        return userRepository.findAll();
    }


    /*
         Rota para alterar seu propio usuario
     */

    @PutMapping("/AlterUser")
    public ResponseEntity<String> AlterUser(@Valid @RequestBody AlterUser alterUser) throws Exception {
       try {
           userService.AlterAcount(ExtractEmail.extrairEmail(), alterUser);
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
       }

        return ResponseEntity.status(HttpStatus.CREATED).body("User Altered");
    }


    /*
        Rota para enviar imagem
     */


    @PostMapping("/favoritos/{id}")
    public ResponseEntity<String> Favoritos(@PathVariable UUID id) throws Exception {
        userService.FavoriteBar(id, ExtractEmail.extrairEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body("Salvo nos favoritos");
    }


    @PostMapping ("/Upload")
    public ResponseEntity <String> upload (@RequestParam("file") MultipartFile file ) throws Exception {
        imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Imagem Salva");
    }




    @GetMapping("/profile-image")
    public ResponseEntity<Resource> getUserProfileImage() throws Exception {
        Resource image = imageService.getImage();
       return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cleanMessage);
    }




}





