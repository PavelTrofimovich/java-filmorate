package ru.yandex.practicum.filmorate.storage.friends;

import java.util.List;

public interface FriendsStorage {
    void addFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);

    List<Integer> getFriends(Integer userId);
}
