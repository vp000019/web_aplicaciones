package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.PostFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.post.Post;
import ua.com.alevel.persistence.entity.user.BaseUser;
import ua.com.alevel.service.PostService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.DataTableRequestUtil;
import ua.com.alevel.util.ImageRenderUtil;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.web.dto.request.PostRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.PostResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostFacadeImpl implements PostFacade {

    private final PostService postService;
    private final UserService userService;

    public PostFacadeImpl(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Override//Преобразование PostRequestDto в Post. Просто одни поля в другие и добавление фотки в файл
    public void create(PostRequestDto postRequestDto) {
        try {
            BaseUser user = userService.findByEmail(postRequestDto.getEmailUser());
            Post post = new Post();
            long indexOfDb = postService.getLastIndex() + 1;
            String pathToImage = ImageRenderUtil.writeImageToFilesAndGetPath(postRequestDto.getMultipartFile(), Long.toString(indexOfDb));
            post.setUserCustomer(user);
            post.setDate(postRequestDto.getDate());
            post.setNamePost(postRequestDto.getName());
            post.setPrice(postRequestDto.getPrice());
            post.setDescription(postRequestDto.getDescription());
            post.setImagePath(pathToImage);
            postService.create(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, PostRequestDto postRequestDto) {
//  Не добавлял фичу обновления пользователя, у меня тож нет
    }

    @Override
    public void delete(Long id) {
        postService.delete(id);
    }

    @Override//Поиск по id поста.
    public PostResponseDto findById(Long id) {
        Post post = postService.findById(id).orElse(null);
        assert post != null;
        //Из полного пути делает путь для UI
        post.setImagePath(post.getImagePath().replaceAll("C:/D/codes/travels_site/src/main/resources/static", ""));
        return new PostResponseDto(post);
    }

    @Override//Поиск всех постов
    public PageData<PostResponseDto> findAll(WebRequest webRequest) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(webRequest);
        DataTableResponse<Post> tableResponse;
        dataTableRequest.setSort("created");
        tableResponse = postService.findAll(dataTableRequest);
        PageData<PostResponseDto> pageData = (PageData<PostResponseDto>) DataTableRequestUtil.initPageData(tableResponse);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : tableResponse.getItems()) {
            post.setImagePath(post.getImagePath().replaceAll("C:/D/codes/travels_site/src/main/resources/static/", ""));
            postResponseDtoList.add(new PostResponseDto(post));
        }
        pageData.setItems(postResponseDtoList);
        return pageData;
    }

    @Override //Не реализовал нигде
    public PageData<PostResponseDto> search(WebRequest webRequest, String userEmail) {
        return null;
    }
}
