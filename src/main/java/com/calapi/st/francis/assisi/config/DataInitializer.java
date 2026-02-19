package com.calapi.st.francis.assisi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.calapi.st.francis.assisi.model.User;
import com.calapi.st.francis.assisi.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner runner(UserRepository repo,
                                    PasswordEncoder encoder) {

        return args -> {

            if (repo.findByUsername("admin").isEmpty()) {

                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode("1234"));
                user.setRole("ROLE_ADMIN");

                repo.save(user);

                System.out.println("Admin user created.");
            }
        };
    }
}

