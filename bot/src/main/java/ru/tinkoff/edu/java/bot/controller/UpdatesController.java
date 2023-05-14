package ru.tinkoff.edu.java.bot.controller;

import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.service.UpdateService;

@RestController
@RequiredArgsConstructor
public class UpdatesController {

    private final UpdateService updateService;

    @PostMapping("api/updates")
    public void sendUpdate(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        updateService.handleUpdate(linkUpdateRequest);
    }
}
