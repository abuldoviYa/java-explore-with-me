package ru.practicum.comment.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.WrongArgumentException;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Transactional
    public void adminDelete(Integer comId) {
        commentRepository.deleteById(comId);
        log.debug("Delete comment with id= {}, SERVICE", comId);
    }

    @Transactional
    public CommentDto create(Long userId, Long eventId, CommentDto commentDto) {
        log.debug("Create comment, SERVICE");
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        Comment comment = commentMapper.toComment(commentDto, user, event);
        comment.setCreated(LocalDateTime.now());
        log.debug("Comment with id = {}, created", comment.getId());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentDto update(Long userId, Long commentId, CommentDto updateText) {
        log.debug("Update comment, SERVICE");
        User user = userService.getUserById(userId);
        Comment comment = getEntityById(commentId);
        CommentDto dto = commentMapper.toCommentDto(comment);
        if (!dto.getUserId().equals(user.getId())) {
            throw new WrongArgumentException("comment must belong to the user");
        }
        comment.setText(updateText.getText());
        log.debug("Comment with id= {}, updated", comment.getId());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    public void deleteCommentById(Long userId, Long commentId) {
        log.debug("Delete comment with id= {}, SERVICE", commentId);
        List<Comment> commentList = commentRepository.getCommentByUserId(userId);
        if (commentList.isEmpty()) {
            throw new ForbiddenException("Comments not found");
        }
        for (Comment comment : commentList) {
            if (!comment.getId().equals(commentId)) {
                throw new WrongArgumentException("comment must belong to the user");
            } else {
                commentRepository.deleteById(commentId);
            }
        }
    }

    @Override
    public CommentDto getCommentById(Long comId) {
        log.debug("Get comment by id= {}, SERVICE", comId);
        return commentMapper.toCommentDto(commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException("comment with id =" + comId + " not found")));
    }

    @Override
    public List<CommentDto> getAllCommentsInEvent(Long eventId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> commentList = commentRepository.getByEventIdOrderByCreatedDesc(eventId, pageable);
        return commentList.stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public Comment getEntityById(Long id) {
        log.debug("Get comment by id= {}, SERVICE", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("comment with id =" + id + " not found"));
    }
}