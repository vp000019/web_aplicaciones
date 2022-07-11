package ua.com.alevel.web.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.PostFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.types.Role;
import ua.com.alevel.web.dto.response.PostResponseDto;
import ua.com.alevel.web.dto.response.UserResponseDto;


@Controller
@RequestMapping("/post")
public class PostController {

    private final PostFacade postFacade;
    private final UserFacade userFacade;

    public PostController(PostFacade postFacade, UserFacade userFacade) {
        this.postFacade = postFacade;
        this.userFacade = userFacade;
    }

    @GetMapping("/{id}")
    //Поиск поста по id
    public String findById(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PostResponseDto postResponseDto = postFacade.findById(id);
        model.addAttribute("post", postResponseDto);
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
        return "../static/cards";
    }
}
