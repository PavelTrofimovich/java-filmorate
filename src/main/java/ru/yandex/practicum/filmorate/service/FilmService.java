package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.likes.LikesStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private static final LocalDate DATE = LocalDate.of(1895, 12, 28);

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesStorage likesStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    public Film createFilm(Film film) {
        validation(film);
        Film filmReturn = filmStorage.createFilm(film);
        Integer id = filmReturn.getId();
        genreStorage.addGenres(id, film.getGenres());
        filmReturn.setGenres(genreStorage.findGenres(id));
        return filmReturn;
    }

    public Film updateFilm(Film film) {
        validation(film);
        checkExistFilm(film.getId());
        filmStorage.updateFilm(film);
        Integer id = film.getId();
        genreStorage.updateGenres(id, film.getGenres());
        film.setGenres(genreStorage.findGenres(id));
        film.setMpa(mpaStorage.getMpaById(film.getMpa().getId()));
        return film;
    }

    public List<Film> getAllFilms() {
        List<Film> films = filmStorage.getAllFilm();
        films.forEach(film -> {
            film.setGenres(genreStorage.findGenres(film.getId()));
            film.setMpa(mpaStorage.getMpaById(film.getMpa().getId()));
        });
        return films;
    }

    public Film getById(Integer id) {
        Film filmReturn = checkExistFilm(id);
        filmReturn.setGenres(genreStorage.findGenres(id));
        filmReturn.setMpa(mpaStorage.getMpaById(filmReturn.getMpa().getId()));
        return filmReturn;
    }

    public void addLike(Integer filmId, Integer userId) {
        checkExistFilm(filmId);
        checkExistUser(userId);
        likesStorage.addLike(filmId,userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        checkExistFilm(filmId);
        checkExistUser(userId);
        likesStorage.deleteLike(filmId,userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        List<Film> result = filmStorage.getAllFilm().stream()
                .sorted(this::comparator)
                .limit(count).collect(Collectors.toCollection(LinkedList::new));

        result.forEach(film -> {
            film.setGenres(genreStorage.findGenres(film.getId()));
            film.setMpa(mpaStorage.getMpaById(film.getMpa().getId()));
        });
        return result;
    }

    protected void validation(Film film) {
        if (film.getReleaseDate().isBefore(DATE)) {
            log.debug("Validation release date error");
            throw new ValidationException("Validation release date error");
        }
    }

    private void checkExistUser(Integer id) {
        try {
            userStorage.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.debug("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    private Film checkExistFilm (Integer id) {
        try {
            return filmStorage.getById(id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Фильм не найден");
            throw new NotFoundException("Фильм не найден");
        }
    }

    private int comparator(Film film, Film film2) {
        return Integer.compare(likesStorage.getLikes(film2.getId()).size(),
                likesStorage.getLikes(film.getId()).size());
    }
}