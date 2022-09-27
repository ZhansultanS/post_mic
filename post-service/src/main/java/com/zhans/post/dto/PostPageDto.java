package com.zhans.post.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.zhans.post.model.Post;
import com.zhans.post.model.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonView(Views.CardPost.class)
public class PostPageDto {

    private List<Post> posts;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
