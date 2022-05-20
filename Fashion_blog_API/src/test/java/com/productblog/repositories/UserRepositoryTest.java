package com.productblog.repositories;

import com.productblog.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
        List<User> userList = new ArrayList<>();
        String firstname = "Almustapha";
        String lastname = "Tukur";
        String email = "atumar4031@gmail.com";
        String role = "admin";
        User user = User.builder()
                .id(1L)
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .role(role)
                .build();
        entityManager.persist(user);

    }

    @Test
    public void shouldReturnUser_givenUserId(){
        User returnUser = userRepository.findById(1L).get();
        assertEquals(returnUser.getFirstName(), "Almustapha");
    }


}