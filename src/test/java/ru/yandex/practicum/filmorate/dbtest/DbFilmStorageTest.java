package ru.yandex.practicum.filmorate.dbtest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.DbFilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DbFilmStorageTest {
    private final DbFilmStorage dbFilmStorage;
    private Film film;
    private final Mpa mpa = new Mpa();

    @BeforeEach
    public void beforeEach() {
        mpa.setId(1);
        mpa.setName("G");
        film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2011, 11, 11))
                .duration(60)
                .mpa(mpa)
                .build();
        film.setId(1);
    }

    @Test
    void getAllFilm() {
        List<Film> films = new ArrayList<>(Collections.singleton(film));
        dbFilmStorage.createFilm(film);
        List<Film> filmsActual = dbFilmStorage.getAllFilm();
        assertEquals(films, filmsActual);
    }

    @Test
    void createFilmTest() {
        Film filmActual = dbFilmStorage.createFilm(film);
        assertEquals(film, filmActual);
    }

    @Test
    void updateFilmTest() {
        dbFilmStorage.createFilm(film);
        film.setDuration(90);
        film.setName("new Name");
        Film filmActual = dbFilmStorage.updateFilm(film);
        assertEquals(film, filmActual);
    }

    @Test
    void getByIdTest() {
        dbFilmStorage.createFilm(film);
        Film filmActual = dbFilmStorage.getById(1);
        assertEquals(film, filmActual);

    }
}