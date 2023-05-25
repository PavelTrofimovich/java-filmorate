package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public abstract class BaseController<T extends BaseModel> {
    private final HashMap<Integer, T> data = new HashMap<>();
    private Integer id = 0;

    public T createModel(T model) {
        model.setId(++id);
        data.put(model.getId(), model);
        return model;
    }

    public ArrayList<T> getDataList() {
        return new ArrayList<>(data.values());
    }

    public void updateModel(T model) {
        Integer modelId = model.getId();
        if (!data.containsKey(modelId)) {
            log.debug("Unknown id");
            throw new ValidationException("Unknown id");
        }
        data.put(modelId, model);
    }
}