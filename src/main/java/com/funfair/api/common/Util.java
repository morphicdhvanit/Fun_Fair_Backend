package com.funfair.api.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class Util {
    
	@Value("${user.phone.number.length}")
	private int length;
	
	@Autowired
	private S3Client s3Client;

	@Value("${do.spaces.bucket}")
	private String bucketName;
	
	
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
	
	public String saveFile(MultipartFile file, String folder) throws IOException {

		 if (file == null || file.isEmpty())
		  return null;

		 String originalFilename =
		  Path.of(file.getOriginalFilename())
		  .getFileName()
		  .toString();

		 String fileName =
		  System.currentTimeMillis() + "_" + originalFilename;

		 String key = folder + "/" + fileName;


		 PutObjectRequest putObjectRequest =
		  PutObjectRequest.builder()
		   .bucket(bucketName)
		   .key(key)
		   .acl("public-read")
		   .contentType(file.getContentType())
		   .build();


		 s3Client.putObject(
		  putObjectRequest,
		  RequestBody.fromInputStream(
		   file.getInputStream(),
		   file.getSize()));


		 return fileName;
		}
	
	public boolean deleteFile(String folder, String fileName) {

		 try {

		  String key = folder + "/" + fileName;

		  DeleteObjectRequest request =
		   DeleteObjectRequest.builder()
		    .bucket(bucketName)
		    .key(key)
		    .build();

		  s3Client.deleteObject(request);

		  return true;

		 } catch (Exception e) {

		  e.printStackTrace();
		 }

		 return false;
		}
}
