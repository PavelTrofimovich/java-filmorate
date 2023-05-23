package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController extends BaseController<User> {

    @GetMapping
    public Collection<User> getAllUsers() {
        log.debug("Users counts {}", getDataList());
        return super.getDataList();
    }

    @PostMapping
    public User addNewUser(@Valid @RequestBody User user) {
        validator(user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        log.info("Creating user {}", user);
        return super.createModel(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validator(user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        updateModel(user);
        log.info("Update user {}", user);
        return user;
    }

    private void validator(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Validation birthday error");
            throw new ValidationException("Validation birthday error");
        }
    }
}