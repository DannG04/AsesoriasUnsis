package AsesoriasUnsis; // Ajusta según tu estructura

import javax.swing.JOptionPane;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")

public class ApiController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/t")
    public List<Map<String, Object>> getAllData() {
        String sql = "SELECT * FROM alumnos";
        return jdbcTemplate.queryForList(sql);
    }
    // Agrega más endpoints según tu aplicación
}