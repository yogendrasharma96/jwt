package com.scaler.authdemo.about;

import com.scaler.authdemo.users.dtos.UserResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class AboutController {

    @GetMapping("")
    String about() {
        return "This is a demo application for Spring Security";
    }

    @GetMapping("/private")
    //this annotation is used to get the authentication object from the security context
    //this returns the principal object which is set in the authentication object
    // we can get same details by using SecurityContextHolder.getContext().getAuthentication().getPrincipal()
    String privateAbout(@AuthenticationPrincipal UserResponseDto user) {
        var username = user.getUsername();
        return "This is private about information for logged in users only" +
                " you are vieweing this as " + username;
    }
}

