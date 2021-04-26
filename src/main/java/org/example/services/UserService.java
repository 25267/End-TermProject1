package org.example.services;

import org.example.exception.IncorrectUsernameException;
import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        Role role = roleRepository.findRole("USER");
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setRoles(Collections.singletonList(role));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.saveAndFlush(newUser);
    }

    public void updateUserName(Long id, String username) {
        User userName = userRepository.findByUsername(username);

        if(userName != null){
            throw new IncorrectUsernameException("this username is busy ");
        }

        User user = userRepository.findById(id).get();
        user.setUsername(username);
        userRepository.saveAndFlush(user);

    }

    public void updateUser(Long id, User user) {
        User userDb = userRepository.findById(id).orElse(null);

        if (userDb != null) {
            userDb.setUsername(user.getUsername());
            userDb.setPassword(passwordEncoder.encode(user.getPassword())); // plaintext password

            userRepository.saveAndFlush(userDb);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User: " + username + " not found!");
        }
        return user;
    }
}

























