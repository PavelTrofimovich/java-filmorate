package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mapper.Mappers;

import java.util.List;

@Component
@AllArgsConstructor
public class DbMpaStorage implements MpaStorage {
    private final JdbcTemplate jdbc;
    private final Mappers mappers;

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM rating_mpa ORDER BY mpa_id";
        return jdbc.query(sql, mappers::mapRowToMpa);
    }

    @Override
    public Mpa getMpaById(int id) {
        String sql = "SELECT * FROM rating_mpa WHERE mpa_id = ?";
        SqlRowSet srs = jdbc.queryForRowSet(sql, id);
        if (srs.next()) {
            return jdbc.queryForObject(sql, mappers::mapRowToMpa, id);
        } else {
            throw new NotFoundException("Рейтинг не найден!");
        }
    }
}