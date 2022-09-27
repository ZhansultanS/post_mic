package com.zhans.post.repository;

import com.zhans.post.model.Post;
import com.zhans.post.model.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p where (:authorId = 0L or p.authorId = :authorId) and p.status = :status")
    Page<Post> findPostsByAuthorIdAndStatus(Long authorId, PostStatus status, Pageable pageable);
}
