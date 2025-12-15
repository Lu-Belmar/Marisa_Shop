package kirisame.magic.user_service.service;

import kirisame.magic.user_service.model.User;
import kirisame.magic.user_service.repository.userRepository;
import kirisame.magic.user_service.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userRepository userRepository;

    public User registerUser(User user) throws Exception {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email is already in use!");
        }
        
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username is already taken!");
        }
        
        if (user.getRole() == null || user.getRole().isEmpty()) {
        user.setRole("USER"); // Por defecto es usuario normal
        }

        return userRepository.save(user);
    }

    public User loginUser(LoginRequest loginRequest) throws Exception {
        // El campo 'email' de loginRequest trae el input del usuario (puede ser user o email)
        String input = loginRequest.getEmail(); 
        String password = loginRequest.getPassword();

        // 1. Intentar buscar por Email
        Optional<User> userByEmail = userRepository.findByEmailAndPassword(input, password);
        if (userByEmail.isPresent()) {
            return userByEmail.get();
        }

        // 2. Si no funciona, intentar buscar por Username
        Optional<User> userByUsername = userRepository.findByUsernameAndPassword(input, password);
        if (userByUsername.isPresent()) {
            return userByUsername.get();
        }

        // 3. Si ninguno coincide, fallar
        throw new Exception("Invalid credentials (username/email or password incorrect)");
    }

    public User updateUser(Long id, User userDetails) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id: " + id));
        
        // Actualizamos los campos permitidos
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhone(userDetails.getPhone());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setAvatar(userDetails.getAvatar());
        // No actualizamos la contraseña aquí por seguridad, a menos que sea una función específica
        
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}