package ua.com.alevel.service.impl;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.post.Post;
import ua.com.alevel.persistence.entity.user.AdminCustomer;
import ua.com.alevel.persistence.entity.user.BaseUser;
import ua.com.alevel.persistence.repository.user.UserRepository;
import ua.com.alevel.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final CrudRepositoryHelper<BaseUser, UserRepository> crudRepositoryHelper;

    public UserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserRepository userRepository,
            CrudRepositoryHelper<BaseUser, UserRepository> crudRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

// Смотреть в PostService
    @Override
    public void create(BaseUser user) {
        if (userRepository.existsByEmail(user.getEmail())
                || userRepository.existsByLogin(user.getLogin())) {
            throw new EntityExistException("this personal is exist");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        crudRepositoryHelper.create(userRepository, user);

    }

    @Override
    public void update(BaseUser user) {
        crudRepositoryHelper.update(userRepository, user);
    }

    @Override
    public void delete(Long id) {
        deleteAllRelations(id);
        crudRepositoryHelper.delete(userRepository, id);
    }

    @Override
    public Optional<BaseUser> findById(Long id) {
        return crudRepositoryHelper.findById(userRepository, id);
    }

    @Override
    public DataTableResponse<BaseUser> findAll(DataTableRequest request) {
        if (MapUtils.isNotEmpty(request.getRequestParamMap())) {
            return crudRepositoryHelper.findAll(userRepository, request, BaseUser.class);
        }
        return crudRepositoryHelper.findAll(userRepository, request);
    }

    @Override
    public BaseUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteAllRelations(Long id) {
        try {
            AdminCustomer user = (AdminCustomer) findById(id).get();
            if (ObjectUtils.isEmpty(user)) {
                return;
            }
            List<Post> postList = user.getPosts().stream().toList();
            for (Post post : postList) {
                user.removePost(post);
            }
            update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BaseUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}
