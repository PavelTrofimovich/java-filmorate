package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseModel {

    @Email
    @NotNull
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday = LocalDate.now();
    private final Set<Integer> friends = new HashSet<>();

    public void addFriend(Integer id) {
        if(id != null && id > 0) {
            friends.add(id);
        } else throw new ValidationException("id = null or id < 0");
    }

    public void removeFriend(Integer id) {
        if(friends.contains(id)) {
            friends.remove(id);
        } else throw new NotFoundException("Friend not found");
    }
}