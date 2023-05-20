package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    @GetMapping
    public Collection<User> getAllUsers() {
        log.debug("Users counts {}", users.values());
        return users.values();
    }

    @PostMapping
    public User addNewUser(@RequestBody User user) {
        validator(user);
        user.setId(++id);
        users.put(id, user);
        log.info("Creating user {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        validator(user);
        Integer userID = user.getId();
        if (!users.containsKey(userID)) {
            log.debug("Unknown id");
            throw new ValidationException("Unknown id");
        }
        users.remove(userID);
        users.put(userID, user);
        log.info("Update user {}", user);
        return user;
    }

    private void validator(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.debug("Validation email error");
            throw new ValidationException("Validation email error");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.debug("Validation login error");
            throw new ValidationException("Validation login error");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Validation birthday error");
            throw new ValidationException("Validation birthday error");
        }
    }
}