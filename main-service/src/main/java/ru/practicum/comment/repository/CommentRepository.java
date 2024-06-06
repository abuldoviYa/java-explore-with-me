package ru.practicum.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.comment.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> getByEventIdOrderByCreatedDesc(Long id, Pageable pageable);

    List<Comment> getUserCommentOrderByUserId(Long userId);

    Optional<Comment> findById(Long id);

    void deleteById(Long id);
}