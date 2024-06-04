package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Table(name = "requests")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_name", nullable = false)
    String app;

    @NotNull
    @Column(nullable = false)
    private String uri;

    @Column(name = "user_ip", nullable = false, length = 15)
    private String ip;

    @NotNull
    @Column(name = "time_stamp")
    private LocalDateTime timestamp;

}
