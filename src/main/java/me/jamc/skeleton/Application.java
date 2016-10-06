package me.jamc.skeleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.jamc.skeleton.config.DatabaseConfig;

@RestController
@SpringBootApplication
@ServletComponentScan
public class Application {

    @Autowired
    private GreetingBean greeting;

    @Autowired
    private DatabaseConfig config;

    @RequestMapping("/")
    String home() {
        return greeting.getWords();
    }

    @RequestMapping("/database")
    String database() {
        return config.getUrl() + " " + config.getUsername() + " " + config.getPassword();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
