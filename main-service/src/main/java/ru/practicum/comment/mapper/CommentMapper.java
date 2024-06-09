package ru.practicum.comment.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "event", expression = "java(event)")
    Comment toComment(CommentDto newCompilationDto, User user, Event event);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "eventId", source = "event.id")
    CommentDto toCommentDto(Comment compilation);
}

