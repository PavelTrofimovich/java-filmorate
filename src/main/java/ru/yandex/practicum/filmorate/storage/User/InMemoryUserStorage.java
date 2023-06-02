package ru.yandex.practicum.filmorate.storage.User;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> data = new HashMap<>();
    protected Integer id = 0;

    @Override
    public User createUser(User user) {
        if (user != null) {
            ++id;
            user.setId(id);
            data.put(id, user);
        } else throw new ValidationException("User = null");
        return user;
    }

    @Override
    public ArrayList<User> getAllUser() {
        return new ArrayList<>(data.values());
    }

    @Override
    public User updateUser(User user) {
        if (!data.containsKey(user.getId())) {
            throw new NotFoundException("Unknown id");
        }
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(Integer id) {
        if (data.containsKey(id)) {
            return data.get(id);
        } else throw new NotFoundException("User not found");
    }
}