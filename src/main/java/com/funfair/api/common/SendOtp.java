package com.funfair.api.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SendOtp {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${msg91.auth-key}")
  private String authKey;

  @Value("${msg91.sender-id}")
  private String senderId;

  @Value("${msg91.route}")
  private String route;

  @Value("${msg91.dlt-template-id}")
  private String dltTemplateId;

  @Value("${msg91.country}")
  private int country;

  @Value("${msg91.validity-minutes}")
  private int validityMinutes;

  public String sendOtp(String mobileNumber, String otp) {
    String name = "Customer";

    String rawMessage = String.format(
        "Dear %s your OTP for ULTRA RAYS ENERGY SOLUTION is %s. It is valid for %d minutes. Please enter this code to proceed with verification. Please dont share with anyone. ULTRA RAYS ENERGY SOLUTION",
        name, otp, validityMinutes);

    System.out.println("Raw Message: " + rawMessage);

    try {
      // Properly URL encode the message
      String encodedMessage = java.net.URLEncoder.encode(rawMessage,
          java.nio.charset.StandardCharsets.UTF_8.toString());

      String uri = UriComponentsBuilder
          .fromUriString("https://control.msg91.com/api/sendhttp.php")
          .queryParam("authkey", authKey)
          .queryParam("mobiles", mobileNumber.startsWith("91") ? mobileNumber : "91" + mobileNumber)
          .queryParam("message", encodedMessage)
          .queryParam("sender", senderId)
          .queryParam("route", route)
          .queryParam("country", country)
          .queryParam("DLT_TE_ID", dltTemplateId)
          .toUriString();

      // System.out.println("Final Encoded URL: " + uri);

      String response = restTemplate.getForObject(uri, String.class);
      // System.out.println("MSG91 Response: " + response);

      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}