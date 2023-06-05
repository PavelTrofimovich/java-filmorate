package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    Film createFilm(Film film);

    ArrayList<Film> getAllFilm();

    Film updateFilm(Film film);

    Film getById(Integer id);
}