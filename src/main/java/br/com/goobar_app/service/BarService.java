package br.com.goobar_app.service;


import br.com.goobar_app.DTOS.AvaliacaoDTO;
import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.ROLE.TypeRole;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.Avalicao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BarService {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private BarRepository barRepository ;

    /*
        Classe responsavel por salvar o Bar no usuario correspondente
     */
    @Transactional
    public BarModel setUserBar(String email, BarModel barModel ) {

        UserModel user = userRepository.findByEmail(email)                                               //Filtra o usuario
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado pelo email: " + email));

        if (user.getBar() == null) {
            user.setBar(new ArrayList<>());
        }
        barModel.setUser(user);
        barModel.setEmail(email);

        List <BarModel> bar = user.getBar();
        user.setRole (TypeRole.CNPJ);
        bar.add (barModel);
        user.setBar(bar);

        return barRepository.save(barModel);    }

     /*
        Classe responsavel por Deletar o Bar no usuario correspondente
     */

    @Transactional
    public String deleteBar(UUID deleteId, String email) {
        Optional<BarModel> bar = barRepository.findById(deleteId);


        if (bar.isPresent()) {
            UserModel user = bar.get().getUser();
            if (user.getEmail().equals(email)) {
                barRepository.delete(bar.get());
                user.setBar(null);
                user.setRole(TypeRole.USUARIO);
                userRepository.save(user);
            }else {
                throw new RuntimeException("Usuario não é dono do bar");
            }

            return "Bar deletado com sucesso!";
        } else {
            return "Bar não encontrado!";
        }

    }
/*
    Altera as configuração do bar
 */
    @Transactional
    public String alterAtributos(UUID idalter,
                                 String email,
                                 BarModel barModel) throws Exception {
        Optional<BarModel> bar = barRepository.findById(idalter);
        if (bar.isPresent()) {
            UserModel user = bar.get().getUser();
            if (user.getEmail().equals(email)) {
                BarModel barsave = bar.get(); // Obtém o bar existente
                // Atualiza os atributos de barsave, não de barModel
                if (barModel.getDescricao() != null) {
                    barsave.setDescricao(barModel.getDescricao());
                }
                if (barModel.getNomebar() != null) {
                    barsave.setNomebar(barModel.getNomebar());
                }
                if (barModel.getArlivre() != null) {
                    barsave.setArlivre(barModel.getArlivre());
                }
                if (barModel.getTv() != null) {
                    barsave.setTv(barModel.getTv());
                }
                if (barModel.getWifi() != null) {
                    barsave.setWifi(barModel.getWifi());
                }
                if (barModel.getPraia() != null) {
                    barsave.setPraia(barModel.getPraia());
                }
                if (barModel.getArcondicionado() != null) {
                    barsave.setArcondicionado(barModel.getArcondicionado());
                }
                if (barModel.getEstacionamento() != null) {
                    barsave.setEstacionamento(barModel.getEstacionamento());
                }

                barRepository.save(barsave); // Salva as alterações no banco de dados
            } else {
                throw new Exception("Usuario não é proprietário do bar");
            }
        } else {
            throw new Exception("Bar não encontrado");
        }
        return "Bar alterado com sucesso!";
    }



    public String setAvaliacao (UUID id, AvaliacaoDTO avaliacaoDTO) throws Exception{
        Optional<BarModel> bar = barRepository.findById(id);

        if (bar.isPresent()) {
            BarModel barNota= bar.get();
            Double newavv =Avalicao.avaliacao(avaliacaoDTO.avaliacao(),
                               barNota.getAvaliacao(), barNota.getNumerodeavaliacao()
                                );
            barNota.setAvaliacao(newavv);
            barNota.setNumerodeavaliacao(barNota.getNumerodeavaliacao() + 1);

            barRepository.save(barNota);

        }else {
            return "não foi possivel realizar avalicao";
        }


        return "Nota lançada com sucesso";
    }
}