package croco.restau.yummy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	  // Même valeur par défaut et même clé de configuration que FileStorageService,
    // pour être certain que ce qui est écrit sur le disque est bien ce qui est servi.
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Permet d'accéder aux images via http://localhost:9000/images/meals/nom-fichier.jpg
        registry.addResourceHandler("/images/meals/**")
                .addResourceLocations("file:" + uploadDir + "/meals/");
        registry.addResourceHandler("/images/chefs/**")
                .addResourceLocations("file:" + uploadDir + "/chefs/");
    }

}
