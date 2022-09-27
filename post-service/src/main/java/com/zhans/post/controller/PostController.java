package com.zhans.post.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zhans.post.model.Views;
import com.zhans.post.dto.FilterDto;
import com.zhans.post.dto.PostPageDto;
import com.zhans.post.model.Post;
import com.zhans.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;

    @GetMapping
    @JsonView(Views.CardPost.class)
    public PostPageDto getAll(
            @RequestParam(required = false, defaultValue = "0") Long authorId,
            @RequestParam(required = false, defaultValue = "active") String status,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        FilterDto filter = new FilterDto(authorId, status);
        return postService.getPosts(filter, pageable);
    }

    @GetMapping("/{id}")
    @JsonView(Views.FullPost.class)
    public Post getOne(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}
