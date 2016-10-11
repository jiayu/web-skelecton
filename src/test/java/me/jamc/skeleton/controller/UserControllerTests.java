package me.jamc.skeleton.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import me.jamc.skeleton.model.User;
import me.jamc.skeleton.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private UserRepository repo;

    @Test
    public void addUserTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "authed");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> resp = client
                .exchange("/api/user/Michael/Jordan", HttpMethod.PUT, entity, Boolean.class);
        assertThat(resp.getBody()).isTrue();
        User u = repo.findOne(1);
        assertThat(u.getFirstName()).isEqualTo("Michael");
        assertThat(u.getLastName()).isEqualTo("Jordan");
    }

    @Test
    public void getUserTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "authed");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<User> resp = client
                .exchange("/api/user/1", HttpMethod.GET, entity, User.class);
        User u = resp.getBody();
        assertThat(u.getFirstName()).isEqualTo("Michael");
        assertThat(u.getLastName()).isEqualTo("Jordan");
    }
}
