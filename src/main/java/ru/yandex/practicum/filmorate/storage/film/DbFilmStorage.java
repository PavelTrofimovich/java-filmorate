package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.mapper.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbc;

    @Override
    public Film createFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(toMap(film)).intValue());
        return film;
    }

    @Override
    public ArrayList<Film> getAllFilm() {
        String sql = "SELECT * FROM films JOIN rating_mpa AS mpa ON mpa.mpa_id = films.mpa_id";
        return new ArrayList<>(jdbc.query(sql, Mappers::mapRowToFilm));
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE films SET name_film = ?, description = ?, release_date = ?, "
                + "duration = ?, mpa_id = ? WHERE film_id = ?";
        jdbc.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());
        return film;
    }

    @Override
    public Film getById(Integer id) {
        String sql = "SELECT * FROM films JOIN rating_mpa AS mpa ON mpa.mpa_id = films.mpa_id WHERE film_id = ?";
        return jdbc.queryForObject(sql, Mappers::mapRowToFilm, id);
    }

    @Override
    public List<Film> getPopularFilm(Integer count) {
        String sql = "SELECT * FROM films" +
                " LEFT JOIN (SELECT film_id, COUNT(*) AS like_count" +
                " FROM likes " +
                " GROUP BY film_id) likes " +
                " ON likes.film_id = films.film_id " +
                " JOIN rating_mpa AS r ON r.mpa_id = films.mpa_id " +
                " ORDER BY like_count DESC LIMIT ? ;";
        return jdbc.query(sql, Mappers::mapRowToFilm, count);
    }

    private Map<String, Object> toMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("name_film", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("mpa_id", film.getMpa().getId());
        return values;
    }
}