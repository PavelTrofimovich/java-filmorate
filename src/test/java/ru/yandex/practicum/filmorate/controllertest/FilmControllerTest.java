package ru.yandex.practicum.filmorate.controllertest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.User.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;

public class FilmControllerTest {

    private FilmController filmController;
    private Film film;

    @Test
    void validationTestFilmDate() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
        film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(12)
                .build();
        Assertions.assertDoesNotThrow(() -> filmController.addNewFilm(film));
        film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1894, 12, 28))
                .duration(12)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }
}