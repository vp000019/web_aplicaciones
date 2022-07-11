package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.web.dto.request.UserRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.UserResponseDto;

public interface UserFacade extends CrudFacade<UserRequestDto, UserResponseDto> {

    UserResponseDto findById(Long id);

    void update(Long id, UserRequestDto userRequestDto);

    PageData<UserResponseDto> findAll(WebRequest webRequest);

    UserResponseDto findByEmail(String userEmail);

    UserResponseDto findByLogin(String login);

    void travelRegistration(Long postId, String emailUser);
}
