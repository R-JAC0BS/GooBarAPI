package br.com.goobar_app.service;


import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.ROLE.TypeRole;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BarService {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private BarRepository barRepository ;




    public BarModel setUserBar(String email, BarModel barModel) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado pelo email: " + email));

        barModel.setUser(user);
        user.setRole (TypeRole.CNPJ);
        user.setBar(barModel);
        return barRepository.save(barModel);
    }}