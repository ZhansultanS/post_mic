package com.zhans.post.service;

import com.zhans.post.dto.FilterDto;
import com.zhans.post.dto.PostPageDto;
import com.zhans.post.model.Post;
import com.zhans.post.model.PostStatus;
import com.zhans.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BasePostService implements PostService {

    private final PostRepository postRepository;
    private final ValidationService validationService;

    @Override
    public PostPageDto getPosts(FilterDto filter, Pageable pageable) {
        PostStatus status = PostStatus.fromString(filter.getStatus());
        Page<Post> postsPage = postRepository.findPostsByAuthorIdAndStatus(filter.getAuthorId(), status, pageable);
        return new PostPageDto(postsPage.getContent(), pageable.getPageNumber(), postsPage.getTotalPages(), postsPage.getTotalElements());
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found")
        );
    }

    @Override
    public Post create(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        post.setStatus(PostStatus.WAITING);
        if (post.getContent().length() > 200)
            post.setContentInCard(post.getContent().substring(0, 200) + "...");
        else
            post.setContentInCard(post.getContent());
        postRepository.saveAndFlush(post);
        validationService.validatePost(post);
        return post;
    }
}
