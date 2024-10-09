package br.com.goobar_app.Controllers;


import br.com.goobar_app.DTOS.AvaliacaoDTO;
import br.com.goobar_app.DTOS.BarDto;
import br.com.goobar_app.DTOS.EnderecoDTO;
import br.com.goobar_app.Models.BarModel;

import br.com.goobar_app.Models.EnderecoModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.ExtractEmail;
import br.com.goobar_app.service.BarService;
import br.com.goobar_app.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity <String>  registerBar(@RequestBody BarDto bar) throws Exception {
        //Copia os propiedades do BarDto para o barModel
        BeanUtils.copyProperties(bar, barModel);
        //Envia o Email e o objeto bar para classe barService
        barService.setUserBar(ExtractEmail.extrairEmail(), barModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(ExtractEmail.extrairEmail());
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

    /*
        End point para fazer alteração nos atributos do produto
     */
    @PutMapping("alter/{id}")
    public ResponseEntity<String> alterBar(@PathVariable UUID id, @RequestBody BarModel bar) throws Exception {
        try {
            barService.alterAtributos(id, ExtractEmail.extrairEmail(),bar);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Bar Alterado com sucesso");

    }






    @PutMapping("avaliacao/{id}")
    public ResponseEntity<Double> avaliacaoBar(@PathVariable UUID id, @RequestBody AvaliacaoDTO avaliacaoDTO) throws Exception {
        try {
            barService.setAvaliacao(id,avaliacaoDTO);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }






    @PostMapping("location/{id}")
    public ResponseEntity <EnderecoModel> enderecoBar(@PathVariable UUID id,@RequestBody EnderecoDTO enderecoDTO) throws Exception {
            barService.setEndereco(id, enderecoDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }



    @GetMapping("/findBar")
    public List<BarModel> getUsers() {
        return barRepository.findAll();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cleanMessage);
    }

}
