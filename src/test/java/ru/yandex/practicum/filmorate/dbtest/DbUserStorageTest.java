package ru.yandex.practicum.filmorate.dbtest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DbUserStorageTest {
    private final DbUserStorage dbUserStorage;
    private User user;

    @BeforeEach
    public void beforeEach() {
        user = User.builder()
                .login("login")
                .name("name")
                .email("email@email.ru")
                .birthday(LocalDate.of(2011, 11, 11))
                .build();
        user.setId(1);
    }

    @Test
    void getAllUserTest() {
        List<User> users = new ArrayList<>(Collections.singleton(user));
        dbUserStorage.createUser(user);
        List<User> usersActual = dbUserStorage.getAllUser();
        assertEquals(users, usersActual);
    }

    @Test
    void createUserTest() {
        User userActual = dbUserStorage.createUser(user);
        assertEquals(user, userActual);
    }

    @Test
    void updateUserTest() {
        dbUserStorage.createUser(user);
        user.setName("new Name");
        user.setLogin("newLogin");
        User userActual = dbUserStorage.updateUser(user);
        assertEquals(user, userActual);
    }

    @Test
    void getByIdTest() {
        dbUserStorage.createUser(user);
        User userActual = dbUserStorage.getById(1);
        assertEquals(user, userActual);
    }
}