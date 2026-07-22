package com.mini.inventory.bootstrap;

import com.mini.inventory.entity.Role;
import com.mini.inventory.entity.User;
import com.mini.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.USER);

            userRepository.save(user);
        }

    }
}