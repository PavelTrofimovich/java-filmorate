package ru.yandex.practicum.filmorate.storage.User;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    User createUser(User user);

    ArrayList<User> getAllUser();

    User updateUser(User user);

    User getById(Integer id);
}