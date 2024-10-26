package br.com.goobar_app.Controllers;


import br.com.goobar_app.DTOS.AvaliacaoDTO;
import br.com.goobar_app.DTOS.BarDto;
import br.com.goobar_app.DTOS.ComentarioDTO;
import br.com.goobar_app.DTOS.EnderecoDTO;
import br.com.goobar_app.Models.BarModel;

import br.com.goobar_app.Models.ComentarioModel;
import br.com.goobar_app.Models.EnderecoModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.ExtractEmail;
import br.com.goobar_app.service.BarService;
import br.com.goobar_app.service.ComentarioService;
import br.com.goobar_app.service.ImageService;
import br.com.goobar_app.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("Bar")
public class BarController {
    @Autowired
    public final BarService barService;
    @Autowired
    public final UserDetailsService userDetailsService;
    @Autowired
    public  final UserService userService;
    public static final BarModel barModel = new BarModel();
    public final ImageService imageService;
    @Autowired
    public final UserRepository userRepository;
    @Autowired
    private final BarRepository barRepository;

    @Autowired
    public final ComentarioService comentarioService;

    public BarController(BarService barService, UserDetailsService userDetailsService, UserService userService,
                         ImageService imageService, UserRepository userRepository,
                         BarRepository barRepository,
                         ComentarioService comentarioService) {
        super();
        this.barService = barService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.barRepository = barRepository;
        this.comentarioService = comentarioService;
    }


    /*
        Metodo pra registar bar
         */
    @PostMapping("/registerBar")
    public ResponseEntity <String>  registerBar(@RequestBody BarDto bar) throws Exception {
        barModel.setId(null);
        //Copia os propiedades do BarDto para o barModel
        BeanUtils.copyProperties(bar, barModel);
        //Envia o Email e o objeto bar para classe barService
        barService.setUserBar(ExtractEmail.extrairEmail(), barModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(ExtractEmail.extrairEmail());
    }
    /*
        Metodo para deletar o Bar seu propio Bar


     */

    @DeleteMapping("BarDelete/{DeleteId}")
    public String deleteBar(@PathVariable UUID DeleteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Optional) {
            Optional<UserModel> optionalUser = (Optional<UserModel>) authentication.getPrincipal();
            return barService.deleteBar(DeleteId,optionalUser.map(UserModel::getEmail).orElse("Email Não encontrado"));

        }


        return "Bar deletado com sucesso ";
    }

    /*
        End point para fazer alteração nos atributos do produto
     */
    @PutMapping("alter/{id}")
    public ResponseEntity<String> alterBar(@PathVariable UUID id,
                                           @RequestBody BarDto bar) throws Exception {
        try {
            barService.alterAtributos(id, ExtractEmail.extrairEmail(),bar);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Bar Alterado com sucesso");

    }



    @PutMapping("avaliacao/{id}")
    public ResponseEntity<Double> avaliacaoBar(@PathVariable UUID id,
                                               @RequestBody AvaliacaoDTO avaliacaoDTO) throws Exception {
        try {
            barService.setAvaliacao(id,avaliacaoDTO);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /*
        Metodo para adicionar um endereço ao bar
     */

    @PostMapping("location/{id}")
    public ResponseEntity <String> enderecoBar(@PathVariable UUID id,
                                                      @RequestBody EnderecoDTO enderecoDTO) throws Exception {
            try {
                barService.setEndereco(id, enderecoDTO);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("bar salvo com sucesso");
    }

    @PostMapping ("/Upload")
    public ResponseEntity <String> upload (@RequestParam("file") MultipartFile file ) throws Exception {
        imageService.uploadImageBar(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Imagem bar salva");

    }

    @GetMapping("/bar-image/{id}")
    public ResponseEntity<Resource> getUserProfileImage(@PathVariable UUID id) throws Exception {
        Resource image = imageService.getImageBar(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);

    }



    @GetMapping("/findBar")
    public List<BarModel> getUsers() {
        return barRepository.findAll();
    }


    /*
        Adicionar Comentarios a um bar
     */
    @PostMapping("/coments/{id}")
    public ResponseEntity<String> comentsBar(@PathVariable UUID id, @RequestBody ComentarioDTO comentario) throws Exception {
        comentarioService.saveComent(ExtractEmail.extrairEmail(),id,comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentario.toString());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cleanMessage);
    }

}
