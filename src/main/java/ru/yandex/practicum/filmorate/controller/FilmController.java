package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
@Validated
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Запрос на получение всех фильмов");
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film film) {
        log.info("Фильм создан {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма {}", film);
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) {
        log.info("Фильма с id {} получен", id);
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Пользователь {} поставил лайк фильму {}", userId, id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Пользователь {} удалил лайк фильму {}", userId, id);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(defaultValue = "10") @Positive Integer count) {
        log.info("Список первых {} пупулярных фильмов получен", count);
        return filmService.getPopularFilms(count);
    }
}