package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbFriendsStorage implements FriendsStorage {
    private final JdbcTemplate jdbc;

    @Override
    public void addFriend(Integer userId, Integer otherId) {
        String sql = "INSERT INTO friends (user_id, other_id) VALUES (?, ?)";
        jdbc.update(sql, userId, otherId);
    }

    @Override
    public void deleteFriend(Integer userId, Integer otherId) {
        String sql = "DELETE FROM friends WHERE user_id = ? AND other_id = ?";
        jdbc.update(sql, userId, otherId);
    }

    @Override
    public List<Integer> getFriends(Integer userId) {
        String sql = "SELECT other_id FROM friends WHERE user_id = ?";
        return jdbc.queryForList(sql, Integer.class, userId);
    }
}
