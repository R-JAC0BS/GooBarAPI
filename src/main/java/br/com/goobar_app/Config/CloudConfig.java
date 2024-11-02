package br.com.goobar_app.Config;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {

    @Value("${cloudinary.cloud.name}")
    private String Cloudname;

    @Value("${cloudinary.api.key}")
    private String ApiKey;

    @Value("${cloudinary.api.secret}")
    private String ApiSecret;


    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put ("cloud_name", Cloudname);
        config.put ("api_key", ApiKey);
        config.put ("api_secret", ApiSecret);
        return new Cloudinary (config);
    }
}
