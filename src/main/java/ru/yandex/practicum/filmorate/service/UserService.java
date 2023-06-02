package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;

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
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
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
        return userStorage.getAllUser().stream()
                .filter(user -> user.getFriends().contains(id))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer userId1, Integer userId2) {
        List<User> listCommonFriend = new ArrayList<>();
        for (Integer id : userStorage.getById(userId1).getFriends()) {
            if (userStorage.getById(userId2).getFriends().contains(id)) {
                listCommonFriend.add(userStorage.getById(id));
            }
        }
        return listCommonFriend;
    }
}