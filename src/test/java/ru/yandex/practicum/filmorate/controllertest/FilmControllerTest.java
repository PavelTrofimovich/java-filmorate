package ru.yandex.practicum.filmorate.controllertest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {

    private FilmController filmController;
    private Film film;

    @Test
    void validationTestFilmDate() {
        filmController = new FilmController();
        film = new Film("name","description", LocalDate.of(1895,12,28),12);
        Assertions.assertDoesNotThrow(() -> filmController.addNewFilm(film));
        film = new Film("name","description", LocalDate.of(1894,12,28),12);
        Assertions.assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }
}
