package ua.com.alevel.persistence.repository.post;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.post.Post;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface PostRepository extends BaseRepository<Post> {

    Post findTopByOrderByIdDesc();

    List<Post> findByNamePostContaining(String namePost);
}
