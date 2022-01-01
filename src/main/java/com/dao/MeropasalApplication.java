package com.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
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
		
	}


}
