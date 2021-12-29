package com.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dao.Emails.EmailDTO;
import com.dao.Emails.EmailService;

@SpringBootApplication
public class MeropasalApplication implements CommandLineRunner {
	
	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(MeropasalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("###### Email sending initiated ######");

		EmailDTO email = new EmailDTO();

		email.setTo("ruainenterprise@gmail.com");
		email.setSubject("Welcome Letter via Spring Boot + FreeMarker");

		// Populate the template data
		Map<String, Object> templateData = new HashMap<>();
		templateData.put("name", "Atul Rai");
		// List of team members...
		List<String> teamMembers = Arrays.asList("Tendulkar", "Manish", "Dhirendra");
		templateData.put("teamMembers", teamMembers);
		templateData.put("location", "India");
		email.setEmailData(templateData);

		// Calling email service
		emailService.sendWelcomeEmail(email);
		
	}

}
