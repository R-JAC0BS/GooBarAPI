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


    public String uploadImageBar(MultipartFile file) throws Exception {
        try {
            Optional<UserModel> userModel = userRepository.findByEmail(ExtractEmail.extrairEmail());
            UserModel user = userModel.get();

            String folderImage = "uploads/BarImage/";
            String fileName = "bar_" + user.getEmail() + "_" + file.getOriginalFilename();
            Path directory = Paths.get(folderImage);
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

    public Resource getImageBar(UUID id) throws Exception {
        Optional<BarModel> barModel = barRepository.findById(id);
        BarModel user = barModel.get();
        try {
            Path path = Paths.get(user.getImagemurl());
            Resource image = new UrlResource(path.toUri());
            return image;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
