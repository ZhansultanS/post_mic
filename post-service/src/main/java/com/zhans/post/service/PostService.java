package com.zhans.post.service;

import com.zhans.post.dto.FilterDto;
import com.zhans.post.dto.PostPageDto;
import com.zhans.post.model.Post;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostPageDto getPosts(FilterDto filter, Pageable pageable);
    Post getPostById(Long id);
    Post create(Post post);
}
