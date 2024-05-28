package com.scm.scm10.helper;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageHelper {

    private String message;

    private MessageColors colors;

    public void removeSession() throws Exception
    {
        HttpSession httpSession=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        httpSession.removeAttribute("message");
    
    }

}
