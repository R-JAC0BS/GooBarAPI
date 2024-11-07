package br.com.goobar_app.service;


import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.ExtractEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;


@Service
public class ImageService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BarRepository barRepository;

    public final CloudNaryService cloudNaryService;
    private final BarModel barModel = new BarModel();

    public ImageService(CloudNaryService cloudNaryService) {
        this.cloudNaryService = cloudNaryService;
    }

    public Resource getImage() throws Exception {
        Optional<UserModel> userModel = userRepository.findByEmail(ExtractEmail.extrairEmail());
        UserModel user = userModel.get();
        try {
            Path path = Paths.get(user.getImagemUrl());
            Resource image = new UrlResource(path.toUri());
            return image;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String uploadImage(MultipartFile file) throws Exception {
        String email = ExtractEmail.extrairEmail();
        userRepository.findByEmail(email).ifPresent(
                user -> {
                    try{
                        String image = cloudNaryService.upload(file);

                        user.setImagemUrl(image);
                        userRepository.save(user);


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return email;
    }





    public void uploadImageBar(MultipartFile file, UUID id) throws Exception {
         barRepository.findById(id).ifPresent(bar -> {
             try {
                 String image = cloudNaryService.upload(file);
                 bar.setImagemurl(image);
                 barRepository.save(bar);

             } catch (Exception e) {
                 throw new RuntimeException(e);
             }});}


    public String getImageBar(UUID id) throws Exception {
        if (id == null){
            throw new Exception("Não existe nenhuma imagem associada ao bar");
        }
        Optional<BarModel> barModel = barRepository.findById(id);
        String url = barModel.get().getImagemurl();
        return url;
}
}
