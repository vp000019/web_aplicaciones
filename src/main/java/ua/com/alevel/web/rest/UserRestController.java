package ua.com.alevel.web.rest;

import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.web.dto.request.UserRequestDto;

@RestController
public class UserRestController {

    private final UserFacade userFacade;

    public UserRestController(UserFacade customerFacade) {
        this.userFacade = customerFacade;
    }

    @PostMapping(value = "/add_user")
    //Регистрация нового пользователя
    public String addUser(@RequestBody UserRequestDto req) {
        try {
            userFacade.create(req);
            return JSONObject.quote("done");
        } catch (EntityExistException e) {
            return JSONObject.quote("this personal is exist");
        }
    }


    @PostMapping("/registrationTravel")
    @PreAuthorize("hasAuthority('developers:read')")
    //Регистрация нового путешествия при клике на БАЙ
    public void registrationTravel(@RequestParam("id") String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userFacade.travelRegistration(Long.parseLong(postId), authentication.getName());
        }
    }
}
