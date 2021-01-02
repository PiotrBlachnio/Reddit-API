package com.piotrblachnio.reddit.repository;

import com.piotrblachnio.reddit.model.Comment;
import com.piotrblachnio.reddit.model.Post;
import com.piotrblachnio.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}