package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {

	private FilmController filmController;
	private Film film;
	private UserController userController;
	private User user;


	@Test
	void contextLoads() {
	}

	@Test
	void validationTestFilmStandard() {
		filmController = new FilmController();
		film = new Film("D","dd", LocalDate.of(2012,11,6), 12);
		filmController.addNewFilm(film);
		Assertions.assertEquals(1,filmController.getAllFilms().size());
	}

	@Test
	void validationTestFilmDate() {
		filmController = new FilmController();
		film = new Film("name","description",LocalDate.of(1895,12,28),12);
		Assertions.assertDoesNotThrow(() -> filmController.addNewFilm(film));
		film = new Film("name","description",LocalDate.of(1894,12,28),12);
		Assertions.assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
	}

	@Test
	void validationTestUserStandard() {
		userController = new UserController();
		user = new User("mail@ya.ru","login","name",LocalDate.of(1990,10,10));
		userController.addNewUser(user);
		Assertions.assertEquals(1,userController.getAllUsers().size());
	}

	@Test
	void validationTestUserEmptyName() {

		user = new User();
		user.setEmail("mail@ya.ru");
		user.setLogin("Login");
		user.setBirthday(LocalDate.of(1990,10,10));
		userController.addNewUser(user);
		Assertions.assertEquals(1,userController.getAllUsers().size());
	}

	@Test
	void validationTestUserEmpty() {
		userController = new UserController();
		user = new User();
		Assertions.assertThrows(ValidationException.class, () -> userController.addNewUser(user));
	}
}
