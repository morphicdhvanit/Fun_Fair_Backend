package com.funfair.api.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Util {
    
	@Value("${user.phone.number.length}")
	private int length;
	
	
	public String getIdFromName(String name) {
		String id = "";
		 for(char c : name.toCharArray()) {
			 if( Character.isLetterOrDigit(c)) {
				 id=id+Character.toString(c);
			 }
		 }
		 
//		String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,. ";
		id = id + RandomStringUtils.random(5, true, true);
		return id;
	}
	
	public String generateRandomNumericId() {
	    // Generate 10 random digits (change length as needed)
	    return RandomStringUtils.randomNumeric(10);
	}


	public boolean isEmailValid(String emailId) {
		boolean valid = false;
		if (emailId.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) // TODO
			valid = true;
		return valid;
	}

	public boolean isPhoneValid(String phone) {
		boolean valid = false;
		if (phone.length() == length  ) {
			valid = true;
		}
		for (char c : phone.toCharArray()) {
			if (!Character.isDigit(c)) {
				valid = false;
			}
		}

		return valid;
	}
	public boolean isAltPhoneValid(String phone) {
		boolean valid = false;
		if (phone.length() == length  || phone.length() == 0 || phone.isEmpty()) {
			valid = true;
		}
		for (char c : phone.toCharArray()) {
			if (!Character.isDigit(c)) {
				valid = false;
			}
		}

		return valid;
	}
	
	public String saveFile(MultipartFile file, String uploadDir) throws IOException {
		System.out.println("path"+uploadDir);
	    if (file == null || file.isEmpty()) return null;

	    String originalFilename = Path.of(file.getOriginalFilename()).getFileName().toString(); // avoid path traversal
	    String fileName = System.currentTimeMillis() + "_" + originalFilename;
	    Path uploadPath = Paths.get(uploadDir);

	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }

	    Path filePath = uploadPath.resolve(fileName);
	    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	    return fileName;  // return relative file name (you can also return full path)
	}
	
	public boolean deleteFile(String uploadDir, String fileName) {

	    if (fileName == null || fileName.isEmpty()) {
	        return false;
	    }

	    try {
	        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();

	        File file = filePath.toFile();
	        if (file.exists()) {
	            return file.delete(); // true if deleted
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
}
