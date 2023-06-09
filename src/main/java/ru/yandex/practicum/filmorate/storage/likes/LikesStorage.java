package ru.yandex.practicum.filmorate.storage.likes;

import java.util.List;

public interface LikesStorage {
    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);

    List<Integer> getLikes(Integer filmId);
}