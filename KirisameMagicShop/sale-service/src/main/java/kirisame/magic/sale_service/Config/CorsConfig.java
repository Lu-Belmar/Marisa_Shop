package kirisame.magic.sale_service.Config; // CAMBIAR EL PAQUETE SEGUN EL SERVICIO (user, product, sale)

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:5173",              // Tu Vite local
                    "http://localhost:3000",              // Por si acaso
                    "https://mercadovkeireact.onrender.com" // TU FRONTEND EN RENDER (¡Importante!)
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}