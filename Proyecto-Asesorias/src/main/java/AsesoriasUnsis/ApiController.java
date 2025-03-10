package AsesoriasUnsis; // Ajusta según tu estructura

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping("/test")
    public String testApi() {
        return "Hola mundo";
    }
    
    // Agrega más endpoints según tu aplicación
}