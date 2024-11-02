package br.com.goobar_app.service;


import br.com.goobar_app.Config.CloudConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;


@Service
public class CloudNaryService {

    private Cloudinary cloudinary;

    @Autowired
    public CloudNaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map <String,Object> uploadImage (File file) throws IOException {
        return cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    }


    /*
        Metodo para salvar e retornar a url salva utilizando Cloudnary

     */
    public String upload(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("CloudNaryService", file.getOriginalFilename());
        file.transferTo(tempFile);
        try {
            Map<String, Object> uploadResult = uploadImage(tempFile);

            return (String) uploadResult.get("secure_url");
        } finally {
            tempFile.delete();
        }
    }
}
