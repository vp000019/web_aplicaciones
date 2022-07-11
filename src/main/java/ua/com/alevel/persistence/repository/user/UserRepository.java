package ua.com.alevel.persistence.repository.user;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.user.BaseUser;
import ua.com.alevel.persistence.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<BaseUser> {

    BaseUser findByLogin(String login);

    BaseUser findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
