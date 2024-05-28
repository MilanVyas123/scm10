package com.scm.scm10.configurations;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm10.entities.Provider;
import com.scm.scm10.entities.User;
import com.scm.scm10.helper.AppConstant;
import com.scm.scm10.repo.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
      
                OAuth2AuthenticationToken auth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;

                String provider=auth2AuthenticationToken.getAuthorizedClientRegistrationId();
                if(provider.equals("github"))
                {
                        DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();
                        user.getAttributes().forEach((key,val)->System.out.println("KEY : "+key+" VALUE : "+val));
        
                        System.out.println("AUTHORITES : "+user.getAuthorities().toString());

                        String name=user.getAttribute("login");
                        String profilePicture=user.getAttribute("avatar_url");
                        String providerId=user.getName();
                        String email=user.getAttribute("email")!=null?user.getAttribute("email"):(name+"@gmail.com");

                        User user2=userRepository.findByEmail(email).orElse(null);
                    if(user2==null)
                    {
                        User user1=new User();
                        user1.setEmail(email);
                        user1.setName(name);
                        user1.setProfilePic(profilePicture);
                        user1.setPassword("password");
                        user1.setEmail_verfied(true);
                        user1.setProvider(Provider.GITHUB);
                        user1.setProviderId(providerId);
                        user1.setEnabled(true);
                        user1.setAbout("This account is created using github");
                        user1.setRoleList(List.of( AppConstant.ROLE_USER));
        
                        userRepository.save(user1);
        
                    }
        
                        
            

                }
                else if(provider.equals("google"))
                {
                    DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();
                
                    user.getAttributes().forEach((key,val)->System.out.println("KEY : "+key+" VALUE : "+val));
        
                    System.out.println("AUTHORITES : "+user.getAuthorities().toString());
        
                    String name=user.getAttribute("name");
                    String email=user.getAttribute("email");
                    String profilePicture=user.getAttribute("picture");
        
                    User user2=userRepository.findByEmail(email).orElse(null);
                    if(user2==null)
                    {
                        User user1=new User();
                        user1.setEmail(email);
                        user1.setName(name);
                        user1.setProfilePic(profilePicture);
                        user1.setPassword("password");
                        user1.setEmail_verfied(true);
                        user1.setProvider(Provider.GOOGLE);
                        user1.setProviderId(user.getName());
                        user1.setEnabled(true);
                        user1.setAbout("This account is created using google");
                        user1.setRoleList(List.of( AppConstant.ROLE_USER));
        
                        userRepository.save(user1);
        
                    }
        
        
                }

                     response.sendRedirect("/user/dashboard");
        
    }

}
