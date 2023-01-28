package com.projectmanager.manager.user;

import com.projectmanager.manager.Task.Task;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository,BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
    }

    public User addUser(User user,String status) {
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        user.setUserRole(UserRole.valueOf(status));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Set<Task> getTasksFromUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Person does not exist"));
        return user.getTasks();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Person does not exist"));
        userRepository.deleteById(id);
    }

    public User updateUser(String name, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Person does not exist"));
        if (name != null && name.length() >= 0 && !name.equalsIgnoreCase(user.getUsername())) {
            user.setName(name);
        }
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws
            UsernameNotFoundException {

        User user= userRepository.findByname(name);

        if(user != null){
            return user;
        }
        throw new
                UsernameNotFoundException("User not exist with name :" +name);
    }
}
