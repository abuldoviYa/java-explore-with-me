package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.MainConstantsUtil.PAGE_DEFAULT_FROM;
import static ru.practicum.util.MainConstantsUtil.PAGE_DEFAULT_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Validated
public class UserAdminController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody NewUserRequest newUserRequest) {
        return userService.create(newUserRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        return userService.getUsers(ids, PageRequest.of(from / size, size));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long userId) {
        userService.deleteById(userId);
    }
}