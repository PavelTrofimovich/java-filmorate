package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
@Validated
public class FilmController {
    private static final LocalDate DATE = LocalDate.of(1895, 12, 28);
    private final FilmService filmService;

    @GetMapping
    public ArrayList<Film> getAllFilms() {
        log.debug("Films counts {}", filmService.getAllFilms());
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film film) {
        validation(film);
        log.info("Creating film {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        validation(film);
        log.info("Update film {}", film);
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) {
        log.info("GET request film {}", id);
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(defaultValue = "10", required = false) @Positive Integer count) {
        return filmService.getPopularFilms(count);
    }

    protected void validation(Film film) {
        if (film.getReleaseDate().isBefore(DATE)) {
            log.debug("Validation release date error");
            throw new ValidationException("Validation release date error");
        }
    }
}