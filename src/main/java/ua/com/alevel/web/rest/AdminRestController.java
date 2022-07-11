package ua.com.alevel.web.rest;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.facade.PostFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.web.dto.request.PostRequestDto;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final UserFacade userFacade;
    private final PostFacade postFacade;

    public AdminRestController(UserFacade userFacade, PostFacade postFacade) {
        this.userFacade = userFacade;
        this.postFacade = postFacade;
    }

    @PostMapping(path = "/addPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('developers:write')")
    //Загрузка фото
    public String uploadPhoto(@ModelAttribute PostRequestDto req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            req.setEmailUser(authentication.getName());
            postFacade.create(req);
            return JSONObject.quote("done");
        }
        return null;
    }
}
