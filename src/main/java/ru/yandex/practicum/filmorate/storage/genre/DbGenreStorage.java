package ru.yandex.practicum.filmorate.storage.genre;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Component
@AllArgsConstructor
public class DbGenreStorage implements GenreStorage {
    private final JdbcTemplate jdbc;

    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genres ORDER BY genre_id";
        return jdbc.query(sql, this::mapRowToGenre);
    }

    @Override
    public Genre getGenreById(Integer id) {
        String sql = "SELECT * FROM genres WHERE genre_id = ?";
        SqlRowSet srs = jdbc.queryForRowSet(sql, id);
        if (srs.next()) {
            return jdbc.queryForObject(sql, this::mapRowToGenre, id);
        } else {
            throw new NotFoundException("Жанр не найден");
        }
    }

    @Override
    public void addGenres(Integer filmId, Set<Genre> genres) {
        String sql = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
        genres.forEach(genre -> jdbc.update(sql, filmId, genre.getId()));
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
        return new HashSet<>(jdbc.query(format(sql, filmId), this::mapRowToGenre));
    }

    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre(rs.getString("name_genre"));
        genre.setId(rs.getInt("genre_id"));
        return genre;
    }
}
