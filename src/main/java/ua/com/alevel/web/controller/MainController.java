package ua.com.alevel.web.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.PostFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.types.Role;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.PostResponseDto;
import ua.com.alevel.web.dto.response.UserResponseDto;

@Controller
@RequestMapping("/")
public class MainController {

    private final PostFacade postFacade;
    private final UserFacade userFacade;

    public MainController(PostFacade postFacade, UserFacade userFacade) {
        this.postFacade = postFacade;
        this.userFacade = userFacade;
    }

    @RequestMapping
    //Главная страница
    public String mainPage(Model model, WebRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PageData<PostResponseDto> postResponseDtoPageData = postFacade.findAll(request);
        model.addAttribute("posts", postResponseDtoPageData);
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserResponseDto user = userFacade.findByEmail(authentication.getName());
            model.addAttribute("isLogin", true);
            model.addAttribute("isAdmin", user.getRole() == Role.ADMIN);
            model.addAttribute("sendTo", "logout");
        } else {
            model.addAttribute("isLogin", false);
            model.addAttribute("isAdmin", false);
            model.addAttribute("sendTo", "login");

        }
        return "../static/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "../static/login";
    }

    @RequestMapping("/register")
    public String register() {
        return "../static/register";
    }
}
