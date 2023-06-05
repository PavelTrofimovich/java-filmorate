package ru.yandex.practicum.filmorate.controllertest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.User.InMemoryUserStorage;

import java.time.LocalDate;

public class UserControllerTest {

    private UserController userController;
    private User user;

    @Test
    void validationTestBirthday() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
        user = User.builder()
                .email("mail@ya.ru")
                .login("log")
                .name("name")
                .birthday(LocalDate.of(1990, 10, 10))
                .build();
        Assertions.assertDoesNotThrow(() -> userController.addNewUser(user));
        user.setBirthday(LocalDate.of(2077, 10, 10));
        Assertions.assertThrows(ValidationException.class, () -> userController.addNewUser(user));
    }

    @Test
    void validationTestLogin() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
        user = User.builder()
                .email("mail@ya.ru")
                .login("log")
                .name("name")
                .birthday(LocalDate.of(1990, 10, 10))
                .build();
        Assertions.assertDoesNotThrow(() -> userController.addNewUser(user));
        user.setLogin("log    ");
        Assertions.assertThrows(ValidationException.class, () -> userController.addNewUser(user));
    }
}