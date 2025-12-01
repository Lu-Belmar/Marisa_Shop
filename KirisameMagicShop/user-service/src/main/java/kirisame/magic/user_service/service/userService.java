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
        
        return userRepository.save(user);
    }

    public User loginUser(LoginRequest loginRequest) throws Exception {
        Optional<User> user = userRepository
            .findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("Invalid email or password");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}