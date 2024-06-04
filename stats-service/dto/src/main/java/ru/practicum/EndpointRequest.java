package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class EndpointRequest {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 25)
    private String app;

    @NotBlank
    @Size(min = 3, max = 25)
    private String uri;

    @NotBlank
    @Length(min = 7, max = 15)
    private String ip;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

}
