package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.post.Post;
import ua.com.alevel.persistence.entity.user.BaseUser;
import ua.com.alevel.persistence.entity.user.UserCustomer;
import ua.com.alevel.service.PostService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.DataTableRequestUtil;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.web.dto.request.UserRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final PostService postService;

    public UserFacadeImpl(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Override//Преобразование UserRequestDto в BaseUser
    public void create(UserRequestDto userRequestDto) {
        BaseUser user = new UserCustomer();
        user.setEmail(userRequestDto.getEmail());
        user.setLogin(userRequestDto.getLogin());
        user.setPassword(userRequestDto.getPassword());
        user.setFirstName(userRequestDto.getFirstName());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setLastName(userRequestDto.getLastName());
        userService.create(user);
    }

    @Override//Реализовал просто так, но на UI этого нет.
    public void update(Long id, UserRequestDto userRequestDto) {
        BaseUser user = userService.findById(id).get();
        user.setEmail(userRequestDto.getEmail());
        user.setLogin(userRequestDto.getLogin());
        userService.update(user);
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public UserResponseDto findById(Long id) {
        BaseUser user = userService.findById(id).orElse(null);
        assert user != null;
        return new UserResponseDto(user);
    }

    @Override
    public PageData<UserResponseDto> findAll(WebRequest webRequest) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(webRequest);
        DataTableResponse<BaseUser> tableResponse;
        if (!checkingForUserSorting(webRequest)) {
            dataTableRequest.setSort("created");
        }
        tableResponse = userService.findAll(dataTableRequest);
        PageData<UserResponseDto> pageData = (PageData<UserResponseDto>) DataTableRequestUtil.initPageData(tableResponse);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (BaseUser baseUser : tableResponse.getItems()) {
            userResponseDtoList.add(new UserResponseDto(baseUser));
        }
        pageData.setItems(userResponseDtoList);
        return pageData;
    }

    @Override
    public UserResponseDto findByEmail(String userEmail) {
        return new UserResponseDto(userService.findByEmail(userEmail));
    }

    @Override
    public UserResponseDto findByLogin(String login) {
        return new UserResponseDto(userService.findByLogin(login));
    }

    @Override //Добавление путешествия(при клике на buy)
    public void travelRegistration(Long postId, String emailUser) {
        BaseUser user = userService.findByEmail(emailUser);
        Post post = postService.findById(postId).get();
        if (user.getTravelsUser().equals(post)) {
            user.removeTravel(post);
        }
        user.addTravel(post);
        userService.update(user);
    }

    private boolean checkingForUserSorting(WebRequest webRequest) {
        String sort = webRequest.getParameter("sort");
        try {
            return sort.equals("login") || sort.equals("role") || sort.equals("email")
                    || sort.equals("id") || sort.equals("created");
        } catch (NullPointerException e) {
            return true;
        }
    }
}
