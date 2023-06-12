package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Genre getById(Integer id) {
        checkExistGenre(id);
        return genreStorage.getGenreById(id);
    }

    public List<Genre> getAllGenre() {
        return genreStorage.getAllGenres();
    }

    private void checkExistGenre(Integer id) {
        try {
            genreStorage.getGenreById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.debug("Жанр не найден");
            throw new NotFoundException("Жанр не найден");
        }
    }
}
