package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    void adminDelete(Integer comId);

    CommentDto create(Long userId, Long eventId, CommentDto commentDto);

    CommentDto update(Long userId, Long commentId, CommentDto commentDto);

    void deleteCommentById(Long userId, Long commentId);

    CommentDto getCommentById(Long comId);

    List<CommentDto> getAllCommentsInEvent(Long eventId, Integer from, Integer size);
}
