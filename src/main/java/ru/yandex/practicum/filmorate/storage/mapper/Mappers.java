package ru.yandex.practicum.filmorate.storage.mapper;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Mappers {
    public static Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("film_id");
        String name = rs.getString("name_film");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("name_mpa");
        Mpa mpa = new Mpa(mpaName);
        mpa.setId(mpaId);
        Film film = Film.builder()
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .mpa(mpa)
                .build();
        film.setId(id);
        return film;
    }

    public static User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
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

    public static Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre(rs.getString("name_genre"));
        genre.setId(rs.getInt("genre_id"));
        return genre;
    }

    public static Mpa mapRowToMpa(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("mpa_id");
        String name = rs.getString("name_mpa");
        Mpa mpa = new Mpa(name);
        mpa.setId(id);
        return mpa;
    }
}