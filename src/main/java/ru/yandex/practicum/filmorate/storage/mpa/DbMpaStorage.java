package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class DbMpaStorage implements MpaStorage {
    private final JdbcTemplate jdbc;

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM rating_mpa ORDER BY mpa_id";
        return jdbc.query(sql, this::mapRowToMpa);
    }

    @Override
    public Mpa getMpaById(int id) {
        String sql = "SELECT * FROM rating_mpa WHERE mpa_id = ?";
        SqlRowSet srs = jdbc.queryForRowSet(sql, id);
        if (srs.next()) {
            return jdbc.queryForObject(sql, this::mapRowToMpa, id);
        } else {
            throw new NotFoundException("Рейтинг не найден!");
        }
    }

    private Mpa mapRowToMpa(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("mpa_id");
        String name = rs.getString("name_mpa");
        Mpa mpa = new Mpa(name);
        mpa.setId(id);
        return mpa;
    }
}