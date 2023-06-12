package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public List<User> getAllUsers() {
        return userStorage.getAllUser();
    }

    public User createUser(User user) {
        validation(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        validation(user);
        checkExistUser(user.getId());
        return userStorage.updateUser(user);
    }

    public User getById(Integer id) {
        checkExistUser(id);
        return userStorage.getById(id);
    }

    public void addFriends(Integer userId1, Integer userId2) {
        checkExistUser(userId1);
        checkExistUser(userId2);
        friendsStorage.addFriend(userId1, userId2);
    }

    public void removeFriend(Integer userId1, Integer userId2) {
        checkExistUser(userId1);
        checkExistUser(userId2);
        friendsStorage.deleteFriend(userId1, userId2);
    }

    public List<User> getFriends(Integer id) {
        checkExistUser(id);
        return friendsStorage.getFriends(id);
    }

    public List<User> getCommonFriends(Integer userId1, Integer userId2) {
        checkExistUser(userId1);
        checkExistUser(userId2);
        return friendsStorage.getCommonFriends(userId1, userId2);
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

    private void checkExistUser(Integer id) {
        try {
            userStorage.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.debug("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
    }
}