package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.post.Post;

import java.util.List;
import java.util.Map;

public interface PostService extends BaseService<Post> {

    Long getLastIndex();

    List<Post> search(Map<String, String[]> queryMap);

    DataTableResponse<Post> findAllForAdmin(DataTableRequest request);
}
