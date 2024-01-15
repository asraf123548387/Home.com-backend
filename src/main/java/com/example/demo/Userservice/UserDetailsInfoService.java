package com.example.demo.Userservice;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserDetailsInfoService implements org.springframework.security.core.userdetails.UserDetailsService {
  @Autowired
  private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetailsInfoService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepo.findByEmail(username);

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
        Optional<User> userOptional = userRepo.findByEmail(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return Collections.singletonList(user.getRoles());
        } else {
            return Collections.emptyList();
        }
    }

//user search
    public List<User> getUsersBySearch(String search) {
        return userRepo.findByUserNameContainingIgnoreCase(search);
    }

    public boolean isEmailAlreadyRegistered(String email) {
        // Use the UserRepository to check if the email is already registered
        return userRepo.existsByEmail(email);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);

    }

    public boolean verifyOtpAndResetPassword(String otp, String newPassword) {
        User user=userRepo.findByOtp(otp);
        if(user!=null){
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setOtp(null);
            userRepo.save(user);
            return true;
        }
        else {

            return false;
        }
    }
}
