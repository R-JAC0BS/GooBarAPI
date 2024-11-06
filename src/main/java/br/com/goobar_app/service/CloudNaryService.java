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

    private static final long MAX_IMAGE = 4 * 1024 * 1024;

    private final Cloudinary cloudinary;

    @Autowired
    private CloudNaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    private Map <String,Object> uploadImage (File file) throws IOException {
        return cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    }

    /*
        Metodo para salvar e retornar a url salva utilizando Cloudnary

     */
    public String upload(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("CloudNaryService", file.getOriginalFilename());
        max_size(file);
        file.transferTo(tempFile);
        try {
            Map<String, Object> uploadResult = uploadImage(tempFile);

            return (String) uploadResult.get("secure_url");
        } finally {
            tempFile.delete();
        }
    }
    private void max_size (MultipartFile file) throws IOException {
        final long size = file.getSize();
        if (size > MAX_IMAGE) {
            throw new IOException("O arquivo deve ser menor que 4mb");
        }
    }
}
