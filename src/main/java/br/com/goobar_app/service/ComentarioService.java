package br.com.goobar_app.service;


import br.com.goobar_app.DTOS.ComentarioDTO;
import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.ComentarioModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.ComenatarioRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComentarioService {

    @Autowired
    private final UserRepository userRepository;


    @Autowired
    private final BarRepository barRepository;

    @Autowired
    private final ComenatarioRepository comenatarioRepository;

    private final ComentarioModel comentarioModel = new ComentarioModel();

    public ComentarioService(UserRepository userRepository, BarRepository barRepository, ComenatarioRepository comenatarioRepository) {
        this.userRepository = userRepository;
        this.barRepository = barRepository;
        this.comenatarioRepository = comenatarioRepository;
    }

    public String saveComent (String email, UUID id, ComentarioDTO comentario){
        Optional<UserModel> userGet = userRepository.findByEmail(email);
        Optional<BarModel> barGet = barRepository.findById(id);
        ComentarioModel comentarioModel = new ComentarioModel();

        if(userGet.isPresent() && barGet.isPresent()){
            UserModel user = userGet.get();
            BarModel bar = barGet.get();


            comentarioModel.setUsuario(user.getUsername());
            comentarioModel.setComentario(comentario.comentario());


            comentarioModel.setUserComent(bar);


            List<ComentarioModel> comentarios = bar.getComentarios();
            if (comentarios == null) {
                comentarios = new ArrayList<>();
            }
            comentarios.add(comentarioModel);


            comenatarioRepository.save(comentarioModel);
            bar.setComentarios(comentarios);
            barRepository.save(bar);
        }

        return "Comentário salvo com sucesso!";
    }
}
