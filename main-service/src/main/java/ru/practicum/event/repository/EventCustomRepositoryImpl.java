package ru.practicum.event.repository;

import ru.practicum.event.enums.EventStateType;
import ru.practicum.event.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventCustomRepositoryImpl implements EventCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Set<Event> getEventsByAdmin(List<Long> users, List<EventStateType> states, List<Long> categories,
                                       LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        Predicate criteria = builder.conjunction();

        if (users != null && !users.isEmpty()) {
            criteria = builder.and(criteria, root.get("initiator").in(users));
        }

        if (states != null && !states.isEmpty()) {
            criteria = builder.and(criteria, root.get("state").in(states));
        }

        if (categories != null && !categories.isEmpty()) {
            criteria = builder.and(criteria, root.get("category").in(categories));
        }

        if (rangeStart != null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
        }

        if (rangeEnd != null) {
            criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
        }

        query.select(root).where(criteria);
        return entityManager.createQuery(query).setFirstResult(from).setMaxResults(size).getResultStream().collect(Collectors.toSet());
    }

    public Set<Event> getEventsByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Integer from, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        Predicate criteria = builder.conjunction();

        if (text != null && !text.isBlank()) {
            Predicate annotation = builder.like(builder.lower(root.get("annotation")), "%" + text.toLowerCase() + "%");
            Predicate description = builder.like(builder.lower(root.get("description")), "%" + text.toLowerCase() + "%");
            criteria = builder.and(criteria, builder.or(annotation, description));
        }

        if (categories != null && !categories.isEmpty()) {
            criteria = builder.and(criteria, root.get("category").in(categories));
        }

        if (paid != null) {
            criteria = builder.and(criteria, root.get("paid").in(paid));
        }

        if (rangeStart == null && rangeEnd == null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate"), LocalDateTime.now()));
        } else {
            if (rangeStart != null) {
                criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
            }

            if (rangeEnd != null) {
                criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
            }
        }

        criteria = builder.and(criteria, root.get("state").in(EventStateType.PUBLISHED));

        query.select(root).where(criteria);
        return entityManager.createQuery(query).setFirstResult(from).setMaxResults(size).getResultStream().collect(Collectors.toSet());
    }
}