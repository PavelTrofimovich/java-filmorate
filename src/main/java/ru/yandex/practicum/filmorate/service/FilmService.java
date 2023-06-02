package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public ArrayList<Film> getAllFilms() {
        return filmStorage.getAllFilm();
    }

    public Film getById(Integer id) {
        if(filmStorage.getById(id) == null) {
            throw new NotFoundException("Нету");
        }
        return filmStorage.getById(id);
    }

    public Film addLike(Integer filmId, Integer userId) {
        if (userStorage.getById(userId) == null || userId < 0) {
            throw new NotFoundException("Not found user");
        }
        Film film = filmStorage.getById(filmId);
        film.addLike(userId);
        return film;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        if (userStorage.getById(userId) == null || userId < 0) {
            throw new NotFoundException("Not found user");
        }
        Film film = filmStorage.getById(filmId);
        if (!film.getLikeUser().contains(userId)) {
            throw new NotFoundException("fefe");
        }
        film.removeLike(userId);
        return film;
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getAllFilm()
                .stream()
                .sorted((film1,film2) -> film2.getLike() - film1.getLike())
                .limit(count).collect(Collectors.toList());
    }
}