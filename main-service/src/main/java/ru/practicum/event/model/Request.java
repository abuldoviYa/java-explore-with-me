package ru.practicum.event.model;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.event.enums.RequestStatusType;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User requester;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatusType status;
}
