package com.scm.scm10.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {

            String username = "";
            OAuth2AuthenticationToken auth2User = (OAuth2AuthenticationToken) authentication;
            OAuth2User  user=(OAuth2User)authentication.getPrincipal();

            if (auth2User.getAuthorizedClientRegistrationId().equals("google")) {
                System.out.println("Getting email from google");
                username=user.getAttribute("email");

            } else if (auth2User.getAuthorizedClientRegistrationId().equals("github")) {
                
                username=user.getAttribute("email")!=null?user.getAttribute("email"):(user.getAttribute("login")+"@gmail.com");

            }

            return username;

        } else {
            return authentication.getName();
        }
    }
}
