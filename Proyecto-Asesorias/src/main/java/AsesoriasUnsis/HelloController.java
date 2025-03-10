package AsesoriasUnsis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String holaMundo() {
        return "¡Hola Mundo desde Spring Boot!";
    }
}