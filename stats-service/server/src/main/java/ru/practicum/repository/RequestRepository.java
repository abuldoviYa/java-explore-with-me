package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.StatsView;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Stats, Long> {
    @Query("SELECT new ru.practicum.StatsView(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM Stats AS s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<StatsView> findUniqueIpRequestsWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatsView(s.app, s.uri, COUNT(s.ip)) " +
            "FROM Stats AS s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<StatsView> findAllRequestsWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatsView(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM Stats AS s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "AND s.uri IN (?3) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<StatsView> findUniqueIpRequestsWithUri(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query("SELECT new ru.practicum.StatsView(s.app, s.uri, COUNT(s.ip)) " +
            "FROM Stats AS s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "AND s.uri IN (?3) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<StatsView> findAllRequestsWithUri(LocalDateTime start, LocalDateTime end, List<String> uri);
}
