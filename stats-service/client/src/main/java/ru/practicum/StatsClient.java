package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


public class StatsClient {
    private final WebClient webClient;

    public StatsClient(@Value("${stats.server.url}") String statsServerUrl) {
        webClient = WebClient.create(statsServerUrl);
    }


    public Mono<Void> postRequest(RequestDTO requestDto) {
        return webClient.post()
                .uri("/hit")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Void.class);
    }


    public Mono<List<RequestOutDTO>> getStats(String start, String end, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/stats")
                            .queryParam("start", start)
                            .queryParam("end", end);
                    if (uris != null)
                        uriBuilder.queryParam("uris", String.join(",", uris));
                    if (unique != null)
                        uriBuilder.queryParam("unique", unique);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(RequestOutDTO.class)
                .collectList();
    }


}