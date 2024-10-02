package br.com.goobar_app.Controllers;


import br.com.goobar_app.DTOS.BarDto;
import br.com.goobar_app.Models.BarModel;

import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.service.BarService;
import br.com.goobar_app.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("Bar")
public class BarController {
    @Autowired
    public BarService barService;
    @Autowired
    public UserDetailsService userDetailsService;
    @Autowired
    public UserService userService;
    public static final BarModel barModel = new BarModel();

    @Autowired
    public UserRepository userRepository;
    @Autowired
    private BarRepository barRepository;

    /*
        Metodo pra registar bar
         */
    @PostMapping("/registerBar")
    public ResponseEntity <String>  registerBar(@RequestBody BarDto bar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Extrair Email do SecurityContextHolder
        Optional<UserModel> optionalUserModel = (Optional<UserModel>) authentication.getPrincipal();
        //DownCast pra filtrar apenas o endereço de email e converter em String
        String Email = optionalUserModel.map(UserModel::getEmail).orElse("");
        //Copia os propiedades do BarDto para o barModel
        BeanUtils.copyProperties(bar, barModel);
        //Envia o Email e o objeto bar para classe barService
        barService.setUserBar(Email, barModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(Email);
    }



    /*
        Metodo para deletar o Bar registrado
     */

    @DeleteMapping("BarDelete/{DeleteId}")
    public String deleteBar(@PathVariable UUID DeleteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Optional) {
            Optional<UserModel> optionalUser = (Optional<UserModel>) authentication.getPrincipal();
            return barService.deleteBar(DeleteId,optionalUser.map(UserModel::getEmail).orElse("Email Não encontrado"));

        }


        return "Testando Delete";
    }

    @PutMapping("alter/{id}")
    public ResponseEntity<String> alterBar(@PathVariable UUID id, @RequestBody BarModel bar) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Extrair Email do SecurityContextHolder
        Optional<UserModel> optionalUserModel = (Optional<UserModel>) authentication.getPrincipal();
        //DownCast pra filtrar apenas o endereço de email e converter em String
        String Email = optionalUserModel.map(UserModel::getEmail).orElse("");
        //Copia os propiedades do BarDto para o barModel
        try {
            barService.alterAtributos(id,Email,bar);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Bar Alterado com sucesso");

    }
    @GetMapping("/getUser")
    public List<BarModel> getUsers() {
        return barRepository.findAll();
    }



}
