package org.lessons.java_final.final_project.security;

import java.util.Optional;

import org.lessons.java_final.final_project.model.User;
import org.lessons.java_final.final_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DatabaseUserDetailService implements UserDetailsService    {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userAttempt = userRepository.findByUsername(username);

        if (userAttempt.isEmpty()){
            throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        }
        return new DatabaseUserDetails(userAttempt.get());
    }
    
}
