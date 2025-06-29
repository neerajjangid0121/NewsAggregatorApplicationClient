package com.example.itt.newsaggregatorclient;

import com.example.itt.newsaggregatorclient.interfaces.ConsoleUI;
import com.example.itt.newsaggregatorclient.ui.HomeUI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsaggregatorclientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		ConsoleUI homeUI = new HomeUI();
		SpringApplication.run(NewsaggregatorclientApplication.class, args);
	}

	@Override
	public void run(String... args) {
		ConsoleUI homeUI = new HomeUI();
		homeUI.showMenu();
	}
}
