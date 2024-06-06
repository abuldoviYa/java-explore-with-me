package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.event.dto.LocationDto;
import ru.practicum.event.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "id", expression = "java(null)")
    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
