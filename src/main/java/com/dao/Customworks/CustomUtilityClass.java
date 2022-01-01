package com.dao.Customworks;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CustomUtilityClass {
	
	public int genrateRandomNumber() {
	    Random r = new Random( System.currentTimeMillis() );
	    return 10000 + r.nextInt(20000);
	}


}
