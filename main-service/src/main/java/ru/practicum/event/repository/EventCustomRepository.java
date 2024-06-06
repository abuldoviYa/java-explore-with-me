package ru.practicum.event.repository;

import ru.practicum.event.enums.EventStateType;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventCustomRepository {
    Set<Event> getEventsByAdmin(List<Long> users, List<EventStateType> states, List<Long> categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    Set<Event> getEventsByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd, Integer from, Integer size);
}
