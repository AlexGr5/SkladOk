package ru.alexey.zadanie.TekstileApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Класс для запуска приложения
 */
@SpringBootApplication
public class TekstileAppApplication {

	/**
	 * Главная функция
	 * @param args		- параметры для main
	 */
	public static void main(String[] args) {
		SpringApplication.run(TekstileAppApplication.class, args);
	}

	/**
	 * Бин для проецирования моделей и ДТО
	 * @return		- объект для использования
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
