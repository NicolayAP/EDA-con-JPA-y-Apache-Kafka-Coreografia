package co.edu.uptc.edakafka.service;

import co.edu.uptc.edakafka.model.Login;
import co.edu.uptc.edakafka.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private LoginEventProducer loginEventProducer;

    // Método para crear un nuevo Login
    public Login createLogin(Login login) {
        // 1. Guardamos el Login en la base de datos local
        Login savedLogin = loginRepository.save(login);
        
        // 2. Disparamos el evento a Kafka avisando que se creó un Login
        loginEventProducer.sendLoginEvent(savedLogin, "LOGIN_CREATED");
        
        return savedLogin;
    }

    // Método para obtener todos los Logins
    public List<Login> getAllLogins() {
        return loginRepository.findAll();
    }

    // Método para buscar un Login asociado a un Customer específico
    public Optional<Login> getLoginByCustomerId(Long customerId) {
        return loginRepository.findByCustomerId(customerId);
    }
}
