package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> getAllUsers() {
        return userStorage.getAllUser();
    }

    public User createUser(User user) {
        validation(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        validation(user);
        return userStorage.updateUser(user);
    }

    public User getById(Integer id) {
        return userStorage.getById(id);
    }

    public User addFriends(Integer userId1, Integer userId2) {
        User user1 = userStorage.getById(userId1);
        User user2 = userStorage.getById(userId2);
        user1.addFriend(userId2);
        user2.addFriend(userId1);
        return user1;
    }

    public User removeFriend(Integer userId1, Integer userId2) {
        User user1 = userStorage.getById(userId1);
        User user2 = userStorage.getById(userId2);
        user1.removeFriend(userId2);
        user2.removeFriend(userId1);
        return user1;
    }

    public List<User> getFriends(Integer id) {
        User user = userStorage.getById(id);
        return user.getFriends().stream().map(this::getById).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer userId1, Integer userId2) {
        User user1 = userStorage.getById(userId1);
        User user2 = userStorage.getById(userId2);
        return user1.getFriends().stream()
                .filter(id -> user2.getFriends().contains(id))
                .map(this::getById).collect(Collectors.toList());
    }

    private void validation(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Validation birthday error");
            throw new ValidationException("Validation birthday error");
        }
        if (user.getLogin().contains(" ")) {
            log.debug("Validation login error");
            throw new ValidationException("Validation login error");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}