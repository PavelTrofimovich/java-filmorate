package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final LocalDate DATE = LocalDate.of(1895, 12, 28);
    private static final Comparator<Film> COMPARATOR_POPULAR_FILMS = ((o1, o2) -> o2.getLike() - o1.getLike());

    public Film createFilm(Film film) {
        validation(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        validation(film);
        return filmStorage.updateFilm(film);
    }

    public ArrayList<Film> getAllFilms() {
        return filmStorage.getAllFilm();
    }

    public Film getById(Integer id) {
        if (filmStorage.getById(id) == null) {
            log.debug("Film not found");
            throw new NotFoundException("Film not found");
        }
        return filmStorage.getById(id);
    }

    public Film addLike(Integer filmId, Integer userId) {
        checkExistUser(userId);
        Film film = filmStorage.getById(filmId);
        film.addLike(userId);
        return film;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        checkExistUser(userId);
        Film film = filmStorage.getById(filmId);
        if (!film.getLikeUser().contains(userId)) {
            log.debug("Like not found");
            throw new NotFoundException("Like not found");
        }
        film.removeLike(userId);
        return film;
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getAllFilm()
                .stream()
                .sorted(COMPARATOR_POPULAR_FILMS)
                .limit(count).collect(Collectors.toList());
    }

    protected void validation(Film film) {
        if (film.getReleaseDate().isBefore(DATE)) {
            log.debug("Validation release date error");
            throw new ValidationException("Validation release date error");
        }
    }

    private void checkExistUser(Integer id) {
        if (userStorage.getById(id) == null || id < 0) {
            log.debug("User not found");
            throw new NotFoundException("User not found");
        }
    }
}