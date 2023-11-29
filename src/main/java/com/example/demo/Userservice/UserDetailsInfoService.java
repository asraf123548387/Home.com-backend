package com.example.demo.Userservice;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserDetailsInfoService implements org.springframework.security.core.userdetails.UserDetailsService {
  @Autowired
  private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepo.findByUserName(username);

        return userInfo.map(com.example.demo.Userservice.UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(Long userId) {

            // Your logic to delete the user from the database
            // You can use userRepository.deleteById(userId) or any other appropriate method
            userRepo.deleteById(userId);

    }
    public List<String> getUserRoles(String username) {
        Optional<User> userOptional = userRepo.findByUserName(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return Collections.singletonList(user.getRoles());
        } else {
            return Collections.emptyList();
        }
    }



}
