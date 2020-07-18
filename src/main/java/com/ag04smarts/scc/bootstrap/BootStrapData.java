package com.ag04smarts.scc.bootstrap;

import com.ag04smarts.scc.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BootStrapData implements CommandLineRunner {
    private final UserRepository userRepository;

    @Autowired
    public BootStrapData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
