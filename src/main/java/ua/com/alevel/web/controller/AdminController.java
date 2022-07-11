package ua.com.alevel.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.PostFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.PostResponseDto;
import ua.com.alevel.web.dto.response.UserResponseDto;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserFacade userFacade;
    private final PostFacade postFacade;

    public AdminController(UserFacade userFacade, PostFacade postFacade) {
        this.userFacade = userFacade;
        this.postFacade = postFacade;
    }


    @RequestMapping("/users")
    @PreAuthorize("hasAuthority('developers:write')")
    //Поиск всех пользователей для админки
    public String findAll(Model model, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            PageData<UserResponseDto> userResponseDtoPageData = userFacade.findAll(webRequest);
            model.addAttribute("isLogin", true);
            model.addAttribute("isAdmin", true);
            model.addAttribute("customers", userResponseDtoPageData);
            model.addAttribute("ifUsers", true);
        }
        return "../static/admin";
    }

    @RequestMapping("/posts")
    @PreAuthorize("hasAuthority('developers:write')")
    //Поиск всех постов для админки
    public String findAllPosts(Model model, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            PageData<PostResponseDto> postResponseDtoPageData = postFacade.findAll(webRequest);
            model.addAttribute("isLogin", true);
            model.addAttribute("isAdmin", true);
            model.addAttribute("posts", postResponseDtoPageData);
            model.addAttribute("ifUsers", true);
        }
        return "../static/adminPost";
    }

    @RequestMapping("/newPost")
    @PreAuthorize("hasAuthority('developers:write')")
    //Создание нового поста
    public String createNewPost(Model model, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("isLogin", true);
            model.addAttribute("isAdmin", true);
            model.addAttribute("ifUsers", true);
        }
        return "../static/new_post";
    }
}
