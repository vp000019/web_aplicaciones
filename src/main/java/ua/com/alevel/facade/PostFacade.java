package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.web.dto.request.PostRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.PostResponseDto;

public interface PostFacade extends CrudFacade<PostRequestDto, PostResponseDto> {

    void update(Long id, PostRequestDto postRequestDto);

    PostResponseDto findById(Long id);

    PageData<PostResponseDto> findAll(WebRequest webRequest);

    PageData<PostResponseDto> search(WebRequest webRequest, String userEmail);
}
