package ru.practicum.event.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "locations", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float lat;

    @Column(nullable = false)
    private Float lon;
}
