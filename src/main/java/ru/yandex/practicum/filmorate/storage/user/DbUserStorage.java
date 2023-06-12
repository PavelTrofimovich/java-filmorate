package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbc;
    private final Mappers mappers;

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(toMap(user)).intValue());
        return user;
    }

    @Override
    public ArrayList<User> getAllUser() {
        String sql = "SELECT * FROM users";
        return new ArrayList<>(jdbc.query(sql, mappers::mapRowToUser));
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET login = ?, name_user = ?, email = ?, birthday = ? WHERE user_id = ?";
        jdbc.update(sql, user.getLogin(), user.getName(), user.getEmail(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public User getById(Integer id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbc.queryForObject(sql, mappers::mapRowToUser, id);
    }

    public Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", user.getId());
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name_user", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }
}