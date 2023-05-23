package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController extends BaseController<Film> {

    private static final LocalDate DATE = LocalDate.of(1895, 12, 28);

    @GetMapping
    public ArrayList<Film> getAllFilms() {
        log.debug("Films counts {}", getDataList());
        return super.getDataList();
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film film) {
        validator(film);
        log.info("Creating film {}", film);
        return super.createModel(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        validator(film);
        super.updateModel(film);
        log.info("Update film {}", film);
        return film;
    }

   private void validator(Film film) {
        if (film.getReleaseDate().isBefore(DATE)) {
            log.debug("Validation release date error");
            throw new ValidationException("Validation release date error");
        }
    }
}
