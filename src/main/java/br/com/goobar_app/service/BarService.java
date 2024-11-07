package br.com.goobar_app.service;


import br.com.goobar_app.CustomException.BarException;
import br.com.goobar_app.CustomException.BarStatus;
import br.com.goobar_app.DTOS.AvaliacaoDTO;
import br.com.goobar_app.DTOS.BarDto;
import br.com.goobar_app.DTOS.EnderecoDTO;
import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.EnderecoModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.ROLE.TypeRole;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.EnderecoRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.Avalicao;
import br.com.goobar_app.components.GlobalException;
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
    private final UserRepository userRepository ;

    @Autowired
    private final BarRepository barRepository ;

    @Autowired
    private final EnderecoRepository enderecoRepository ;

    public BarService(UserRepository userRepository, BarRepository barRepository, EnderecoRepository enderecoRepository) {
        this.userRepository = userRepository;
        this.barRepository = barRepository;
        this.enderecoRepository = enderecoRepository;
    }

    /*
            Classe responsavel por salvar o Bar no usuario correspondente
         */
    @Transactional
    public BarModel setUserBar(String email, BarModel barModel ) throws  BarException {

        UserModel user = userRepository.findByEmail(email)                                               //Filtra o usuario
                .orElseThrow(() ->  new BarException(BarStatus.LOCALIZATION_BAR_ERROR));

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
    public void deleteBar(UUID deleteId, String email) throws BarException {
        Optional<BarModel> bar = barRepository.findById(deleteId);


        if (bar.isPresent()) {
            UserModel user = bar.get().getUser();
            if (user.getEmail().equals(email)) {
                if (user.getBar() == null || user.getBar().isEmpty()) {
                    user.setRole (TypeRole.USUARIO);
                }
                barRepository.deleteById(bar.get().getId());

            }else {
                throw new BarException(BarStatus.USER_OR_BAR_NOT_FOUND);
            }
        }

    }
/*
    Altera as configuração do bar
 */
    @Transactional
    public void alterAtributos(UUID idalter,
                                 String email,
                                 BarDto barModel) throws Exception, BarException {
        BarModel bar = this.barRepository.findById(idalter).orElseThrow(() -> new BarException(BarStatus.USER_NOT_FOUND));

        UserModel user = bar.getUser();
        if (!user.getEmail().equals(email)){
                throw new BarException(BarStatus.USER_OR_BAR_NOT_FOUND);
            }
            // Obtém o bar existente
        if (bar.getDescricao() != null) {
                    bar.setDescricao(barModel.descricao());
                }
        if (bar.getNomebar() != null) {
                    bar.setNomebar(barModel.nomebar());
                }
        if (bar.getArlivre() != null) {
                    bar.setArlivre(barModel.arlivre());
                }
        if (bar.getTv() != null) {
                    bar.setTv(barModel.tv());
                }
        if (bar.getWifi() != null) {
                    bar.setWifi(barModel.wifi());
                }
        if (bar.getPraia() != null) {
                    bar.setPraia(barModel.praia());
                }
        if (bar.getArcondicionado() != null) {
                    bar.setArcondicionado(barModel.arcondicionado());
                }
        if (bar.getEstacionamento() != null) {
                    bar.setEstacionamento(barModel.estacionamento());
                }
        if (bar.getMesabilhar() != null)
                    bar.setMesabilhar(barModel.mesabilhar());
        barRepository.save(bar);
    }



    public String setAvaliacao (UUID id, AvaliacaoDTO avaliacaoDTO) throws BarException {
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
            throw new BarException(BarStatus.AVALIATION_NOT_SEND);
        }
        return "tudo certo";
    }


    @Transactional
    public String setEndereco(UUID id, EnderecoDTO enderecoDTO) throws BarException{
        Optional<BarModel> barOptional = barRepository.findById(id);

        if (enderecoDTO.latitude() == null || enderecoDTO.longitude() == null)
            throw new BarException(BarStatus.LOCALIZATION_BAR_ERROR);

        if (barOptional.isPresent()) {
            BarModel bar = barOptional.get();

            EnderecoModel enderecoModel = new EnderecoModel();
            enderecoModel.setLatitude(enderecoDTO.latitude());
            enderecoModel.setLongitude(enderecoDTO.longitude());
            enderecoModel.setBarModel(bar);

            // Salvar o EnderecoModel
            enderecoRepository.save(enderecoModel);

            // Salvar o BarModel se a relação for um-para-um
            bar.setEndereco(enderecoModel);
            barRepository.save(bar);
        }

        throw new BarException(BarStatus.BAR_NOT_FOUND);
    }

    public List<BarModel> GetFavoritos (String email) throws BarException {
        Optional<UserModel> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserModel userModel = user.get();
            return List.copyOf(userModel.getBarFavoritos());
        }else{
            throw new BarException(BarStatus.FAVORITE_BAR_SAVE);
        }
    }
}