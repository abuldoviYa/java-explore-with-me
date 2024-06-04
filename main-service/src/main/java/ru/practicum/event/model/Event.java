package ru.practicum.event.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.category.model.Category;
import ru.practicum.event.enums.EventStateType;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.util.MainConstantsUtil.*;

@Entity
@Table(name = "events", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_TITLE)
    private String title;

    @Column(nullable = false, length = MAX_LENGTH_ANNOTATION)
    private String annotation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(nullable = false, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(nullable = false)
    private Boolean paid;

    @Column(nullable = false)
    private Integer participantLimit;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStateType state;

    private LocalDateTime publishedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User initiator;

    @Column(nullable = false)
    private Boolean requestModeration;
}
