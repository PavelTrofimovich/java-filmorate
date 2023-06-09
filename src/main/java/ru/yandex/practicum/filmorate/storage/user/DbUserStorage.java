package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DbUserStorage implements UserStorage{
    private final JdbcTemplate jdbc;

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue());
        return user;
    }

    @Override
    public ArrayList<User> getAllUser() {
        String sql = "SELECT * FROM users";
        return new ArrayList<>(jdbc.query(sql, this::mapRowToUser));
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
        return jdbc.queryForObject(sql, this::mapRowToUser, id);
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name_user");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        User user = User.builder()
                .email(email)
                .login(login)
                .name(name)
                .birthday(birthday)
                .build();
        user.setId(id);
        return user;
    }
}