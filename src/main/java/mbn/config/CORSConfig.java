package mbn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CORSConfig implements WebMvcConfigurer {

    private final String psUiBaseUrl;

    @Autowired
    public CORSConfig(@Value("${ps-ui.url}") String psUiBaseUrl) {
        this.psUiBaseUrl = psUiBaseUrl;
    }

    @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000/", psUiBaseUrl)
            .allowedMethods("GET", "POST", "PATCH");
        }
    }
