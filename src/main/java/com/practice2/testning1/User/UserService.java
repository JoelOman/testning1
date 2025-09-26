package com.practice2.testning1.User;


import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String email, String password) throws UserAlreadyExistsException, IllegalArgumentException {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
        else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Ogiltig e-postadress.");
        }
        try{

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userRepository.save(user);

        }
        catch(Exception e){ throw new RuntimeException("Error registering user" +e.getMessage()); }

    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
