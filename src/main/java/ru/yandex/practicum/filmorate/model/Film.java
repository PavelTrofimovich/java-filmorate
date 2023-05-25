package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film extends BaseModel {

    @NotBlank
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private Integer duration;

    public Film(String name, String description, LocalDate releaseDate, Integer duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}