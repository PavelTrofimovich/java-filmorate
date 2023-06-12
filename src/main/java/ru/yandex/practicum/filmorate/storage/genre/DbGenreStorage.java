package ru.yandex.practicum.filmorate.storage.genre;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.Mappers;

import java.util.*;

import static java.lang.String.format;

@Component
@AllArgsConstructor
public class DbGenreStorage implements GenreStorage {
    private final JdbcTemplate jdbc;
    private final Mappers mappers;

    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genres ORDER BY genre_id";
        return jdbc.query(sql, mappers::mapRowToGenre);
    }

    @Override
    public Genre getGenreById(Integer id) {
        String sql = "SELECT * FROM genres WHERE genre_id = ?";
        SqlRowSet srs = jdbc.queryForRowSet(sql, id);
        if (srs.next()) {
            return jdbc.queryForObject(sql, mappers::mapRowToGenre, id);
        } else {
            throw new NotFoundException("Жанр не найден");
        }
    }

    @Override
    public void addGenres(Integer filmId, Set<Genre> genres) {
        String sql = "INSERT INTO film_genres (film_id, genre_id) VALUES ";
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Genre genre : genres) {
            stringJoiner.add("(" + filmId + ", " + genre.getId() + ")");
        }
        genres.forEach(genre -> jdbc.update(sql + stringJoiner.toString()));
    }

    @Override
    public void updateGenres(Integer filmId, Set<Genre> genres) {
        String sql = "DELETE FROM film_genres WHERE film_id = ?";
        jdbc.update(sql, filmId);
        addGenres(filmId, genres);
    }

    @Override
    public Set<Genre> findGenres(Integer filmId) {
        String sql = "SELECT f.genre_id, g.name_genre "
                + "FROM film_genres AS f "
                + "LEFT OUTER JOIN genres AS g ON f.genre_id = g.genre_id "
                + "WHERE f.film_id=%d "
                + "ORDER BY g.genre_id";
        return new HashSet<>(jdbc.query(format(sql, filmId), mappers::mapRowToGenre));
    }

    @Override
    public void findAllGenres(List<Film> filmList) {
        String sql = "SELECT * "
                + "FROM film_genres AS f "
                + "LEFT OUTER JOIN genres AS g ON f.genre_id = g.genre_id";
        Map<Integer, Set<Genre>> map = new HashMap<>();
        for (Film film : filmList) {
            map.put(film.getId(), new HashSet<Genre>());
        }
        SqlRowSet rs = jdbc.queryForRowSet(sql);
        while (rs.next()) {
            int id = rs.getInt("film_id");
            if (map.containsKey(id)) {
                Genre genre = new Genre(rs.getString("name_genre"));
                genre.setId(rs.getInt("genre_id"));
                map.get(id).add(genre);
            }
        }
        for (Film film : filmList) {
            film.setGenres(map.get(film.getId()));
        }
    }
}