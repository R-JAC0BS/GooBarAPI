package br.com.goobar_app.service;


import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.ExtractEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;



@Service
public class ImageService {
    @Autowired
    UserRepository userRepository;
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
        try {
            Optional<UserModel> userModel = userRepository.findByEmail(ExtractEmail.extrairEmail());
            UserModel user = userModel.get();

            String folder = "uploads/UserImage/";
            String fileName = "user_" + user.getEmail() + "_" + file.getOriginalFilename();
            Path directory = Paths.get(folder);
            if (Files.notExists(directory)) {
                Files.createDirectories(directory);
            }

            Path path = directory.resolve(fileName);
            Files.write(path, file.getBytes());

            user.setImagemUrl(path.toString());
            userRepository.save(user);
            return userModel.get().getImagemUrl();
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }


}
