package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaService {
    private final MpaStorage mpaStorage;

    public Mpa getById(Integer id) {
        checkExistMpa(id);
        return mpaStorage.getMpaById(id);
    }

    public List<Mpa> getAllGenre() {
        return mpaStorage.getAllMpa();
    }

    private void checkExistMpa(Integer id) {
        try {
            mpaStorage.getMpaById(id);
        } catch (EmptyResultDataAccessException exception) {
            log.debug("Рейтинг MPA не найден");
            throw new NotFoundException("Рейтинг MPA не найден");
        }
    }
}
