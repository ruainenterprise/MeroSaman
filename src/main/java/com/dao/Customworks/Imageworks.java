package com.dao.Customworks;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class Imageworks {
	
	private String encodeImage(byte[] imageByteArray, String fileType) {
        return "data:" + fileType + ";base64," + Base64.getEncoder().encodeToString(imageByteArray);
    }

}
