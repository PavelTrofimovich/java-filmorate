package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer id = 0;

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.debug("Films counts {}", films.values());
        return films.values();
    }

    @PostMapping
    public Film addNewFilm(@RequestBody Film film) {
        validator(film);
        film.setId(++id);
        films.put(id, film);
        log.info("Creating film {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        validator(film);
        Integer filmId = film.getId();
        if (!films.containsKey(filmId)) {
            log.debug("Unknown id");
            throw new ValidationException("Unknown id");
        }
        films.remove(filmId);
        films.put(filmId, film);
        log.info("Update film {}", film);
        return film;
    }

    private void validator(Film film) {
        if (film.getName().isEmpty()) {
            log.debug("Validation name error");
            throw new ValidationException("Validation name error");
        }
        if (film.getDescription().length() > 200 || film.getDescription().length() < 1) {
            log.debug("Validation description error");
            throw new ValidationException("Validation description error");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Validation release date error");
            throw new ValidationException("Validation release date error");
        }
        if (film.getDuration() < 0) {
            log.debug("Validation duration error");
            throw new ValidationException("Validation duration error");
        }
    }
}
