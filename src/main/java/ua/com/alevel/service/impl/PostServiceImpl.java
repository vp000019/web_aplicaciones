package ua.com.alevel.service.impl;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.post.Post;
import ua.com.alevel.persistence.repository.post.PostRepository;
import ua.com.alevel.service.PostService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CrudRepositoryHelper<Post, PostRepository> crudRepositoryHelper;

    public PostServiceImpl(PostRepository postRepository, CrudRepositoryHelper<Post, PostRepository> crudRepositoryHelper) {
        this.postRepository = postRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

//Получение уже заполненного поста и занос его в БД
    @Override
    public void create(Post post) {
        crudRepositoryHelper.create(postRepository, post);
    }

    //Получение уже заполненного поста и обновление его в БД

    @Override
    public void update(Post post) {
        crudRepositoryHelper.update(postRepository, post);
    }

//    Получение ИД поста и удаление его из БД
    @Override
    public void delete(Long id) {
        crudRepositoryHelper.delete(postRepository, id);
    }

//    Поиск поста по id
    @Override
    public Optional<Post> findById(Long id) {
        return crudRepositoryHelper.findById(postRepository, id);
    }

//    Поиск всех постов
    @Override
    public DataTableResponse<Post> findAll(DataTableRequest request) {
        DataTableResponse<Post> dataTableResponse = responseFill(request);
//Получение всех постов и фильтрация тех, где visible правдиво
        dataTableResponse.getItems().stream().filter(Post::getVisible).collect(Collectors.toList());

        return dataTableResponse;
    }

//    Получение id последнего добавленного поста
    @Override
    public Long getLastIndex() {
        Long id;
        try {
            id = postRepository.findTopByOrderByIdDesc().getId();
            return id;
        } catch (NullPointerException e) {
            return 0L;
        }
    }

    //Это для спецификаций, оно вам не надо
    @Override
    public List<Post> search(Map<String, String[]> queryMap) {
        String[] searchImage = queryMap.get("name");
        return postRepository.findByNamePostContaining(searchImage[0]);
    }

    //Тоже самое, что и findAll, только без фильтрации по visible
    @Override
    public DataTableResponse<Post> findAllForAdmin(DataTableRequest request) {
        DataTableResponse<Post> dataTableResponse = responseFill(request);
        return dataTableResponse;
    }

    //Ну эта функция для findAll, чтоб не писать 1 и тот же код 2 раза
    private DataTableResponse<Post> responseFill(DataTableRequest request) {
        if (MapUtils.isNotEmpty(request.getRequestParamMap())) {
            return crudRepositoryHelper.findAll(postRepository, request, Post.class);
        } else {
            return crudRepositoryHelper.findAll(postRepository, request);
        }
    }
}
