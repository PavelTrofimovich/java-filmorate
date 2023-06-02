package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> data = new HashMap<>();
    protected Integer id = 0;

    @Override
    public Film createFilm(Film film) {
        ++id;
        film.setId(id);
        data.put(id, film);
        return film;
    }

    @Override
    public ArrayList<Film> getAllFilm() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Film updateFilm(Film film) {
        if (!data.containsKey(film.getId())) {
            throw new NotFoundException("Unknown id");
        }
        data.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(Integer id) {
        if(data.containsKey(id)) {
            return data.get(id);
        } else throw new NotFoundException("Film not found");
    }
}