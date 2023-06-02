package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film extends BaseModel {

    @NotBlank
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    private Set<Integer> likeUser = new HashSet<>();

    public Integer getLike() {
        return likeUser.size();
    }

    public void addLike(Integer id) {
        if (id != null && id > 0) {
            likeUser.add(id);
        } else {
            throw new ValidationException("id = null or id < 0");
        }
    }

    public void removeLike(Integer id) {
        if (likeUser.contains(id)) {
            likeUser.remove(id);
        } else {
            throw new NotFoundException("Like not found");
        }
    }
}