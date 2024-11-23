package br.com.goobar_app.Controllers;


import br.com.goobar_app.CustomException.BarException;
import br.com.goobar_app.CustomException.BarStatus;
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
import br.com.goobar_app.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public final BarService barService;
    public final UserDetailsService userDetailsService;
    public  final UserService userService;
    public static final BarModel barModel = new BarModel();
    public final CloudNaryService cloudNaryService;
    public final ImageService imageService;
    public final UserRepository userRepository;
    private final BarRepository barRepository;
    public final ComentarioService comentarioService;

    public BarController(BarService barService, UserDetailsService userDetailsService, UserService userService, CloudNaryService cloudNaryService,
                         ImageService imageService, UserRepository userRepository,
                         BarRepository barRepository,
                         ComentarioService comentarioService) {
        super();
        this.barService = barService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.cloudNaryService = cloudNaryService;
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.barRepository = barRepository;
        this.comentarioService = comentarioService;
    }


    /*
        Metodo pra registar bar
         */
    @PostMapping("/registerBar")
    public ResponseEntity <UUID>  registerBar(@RequestBody BarDto bar) throws Exception {
        barModel.setId(null);
        //Copia os propiedades do BarDto para o barModel
        BeanUtils.copyProperties(bar, barModel);
        //Envia o Email e o objeto bar para classe barService
        var barS = barService.setUserBar(ExtractEmail.extrairEmail(), barModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(barS);
    }
    /*
        Metodo para deletar o Bar seu propio Bar


     */

    @DeleteMapping("BarDelete/{DeleteId}")
    public ResponseEntity<String> deleteBar(@PathVariable UUID DeleteId) throws BarException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Optional) {
            Optional<UserModel> optionalUser = (Optional<UserModel>) authentication.getPrincipal();
            barService.deleteBar(DeleteId,optionalUser.map(UserModel::getEmail).orElse("Email Não encontrado"));
            return ResponseEntity.status(HttpStatus.OK).body("Bar deletado com sucesso");

        }

        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body("Não foi possivel deletar o bar");

    }

    /*
        End point para fazer alteração nos atributos do produto
     */
    @PutMapping("alter/{id}")
    public ResponseEntity<String> alterBar(@PathVariable UUID id, @RequestBody BarDto bar) throws Exception {
        try {
            barService.alterAtributos(id, ExtractEmail.extrairEmail(),bar);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Bar Alterado com sucesso");

    }



    @PutMapping("avaliacao/{id}")
    public ResponseEntity<String> avaliacaoBar(@PathVariable UUID id,
                                               @RequestBody AvaliacaoDTO avaliacaoDTO) throws Exception {
        try {
            barService.setAvaliacao(id,avaliacaoDTO);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BarStatus.BAR_AVALAIATION.toString());
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


    /*
        Metodo responsavel por coletar o id e a imagem e enviar para a camada imageService
     */
    @PostMapping ("/Upload/{id}")
    public ResponseEntity <String> upload (@RequestParam("file") MultipartFile file
                                           ,@PathVariable UUID id

                                            ) throws Exception {
        imageService.uploadImageBar(file, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Imagem bar salva com sucesso");

    }

    @GetMapping("/bar-image/{id}")
    public ResponseEntity<String> getUserProfileImage(@PathVariable UUID id) throws Exception {
        return ResponseEntity.status(HttpStatus.FOUND).body(imageService.getImageBar(id));

    }


    @GetMapping("/MyBar")
    public ResponseEntity<List> myBar() throws Exception {
        Optional <UserModel> bar = userRepository.findByEmail(ExtractEmail.extrairEmail());
        return ResponseEntity.status(HttpStatus.OK).body(bar.get().getBar());
    }


    @GetMapping("/findBar")
    public ResponseEntity <Page <BarModel>> getUsers() {
        Pageable pageable = PageRequest.of(0, 20);
        Page <BarModel> bars = barRepository.findAllByEnderecoIsNotNullAndImagemurlIsNotNull(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(bars);
    }


    /*
        Adicionar Comentarios a um bar
     */
    @PostMapping("/coments/{id}")
    public ResponseEntity<String> comentsBar(@PathVariable UUID id, @RequestBody ComentarioDTO comentario) throws Exception {
        comentarioService.saveComent(ExtractEmail.extrairEmail(),id,comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentario.toString());
    }

    @GetMapping ("/favoritos")
    public List<BarModel> getFavoritos() throws Exception {
        return barService.GetFavoritos(ExtractEmail.extrairEmail());
    }

    @GetMapping("/barSelect/{id}")
    public ResponseEntity<BarModel> barSelect(@PathVariable UUID id) throws Exception {
        BarModel barModel1 = barRepository.findById(id).orElseThrow(
                () -> new Exception("não foi possivel localizar o bar")
        );
        return ResponseEntity.status(HttpStatus.OK).body(barModel1);
    }

    @GetMapping ("/populares")
    public ResponseEntity <Page<BarModel>> getPopulares(
            @RequestParam (required = false,defaultValue = "2.0") Double nota
    ) throws Exception {
        Pageable peage = PageRequest.of(0,40);
        Page <BarModel> bar = barRepository.findByAvaliacaoGreaterThan(nota, peage);
        return ResponseEntity.status(HttpStatus.OK).body(bar);
    }

    /*
    METODO UNICO PARA FAZER REGISTRO COMPLETO DO BAR FOTO/LOC/COMPLEMENTOS
     */
   /*
    @PostMapping ("/FRegsiter")
    public ResponseEntity<String> FRegister(@RequestParam("bar") String barJson,
                                            @RequestParam("file") MultipartFile file) throws Exception {

        // Converte o JSON para BarDto
        ObjectMapper objectMapper = new ObjectMapper();
        BarDto bar = objectMapper.readValue(barJson, BarDto.class);

        // Copia as propriedades do BarDto para o BarModel
        BeanUtils.copyProperties(bar, barModel);

        // Chama o serviço para registrar o bar
        barService.FRegisterBar(ExtractEmail.extrairEmail(), barModel, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(BarStatus.BAR_CREATE.toString());
    }
       */
}
