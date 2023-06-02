package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public abstract class BaseModel {

    @Positive
    Integer id;
}