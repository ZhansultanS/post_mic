package com.zhans.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    @JsonView(Views.Common.class)
    private Long id;
    @JsonView(Views.Common.class)
    private Long authorId;

    @JsonView(Views.Common.class)
    private String title;
    @JsonView(Views.FullPost.class)
    private String content;
    @JsonView(Views.CardPost.class)
    private String contentInCard;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private PostStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonView(Views.Common.class)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonView(Views.FullPost.class)
    private LocalDateTime modifiedAt;
}
