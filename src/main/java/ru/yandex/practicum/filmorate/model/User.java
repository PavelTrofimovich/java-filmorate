package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday = LocalDate.now();
}
