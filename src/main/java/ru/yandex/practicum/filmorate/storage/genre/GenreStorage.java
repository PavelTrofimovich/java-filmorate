package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {
    List<Genre> getAllGenres();

    Genre getGenreById(Integer id);

    void addGenres(Integer filmId, Set<Genre> genres);

    void updateGenres(Integer filmId, Set<Genre> genres);

    Set<Genre> findGenres(Integer filmId);
}