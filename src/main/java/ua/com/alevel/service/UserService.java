package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.user.BaseUser;

public interface UserService extends BaseService<BaseUser> {

    BaseUser findByEmail(String email);

    void deleteAllRelations(Long id);

    BaseUser findByLogin(String login);

}
