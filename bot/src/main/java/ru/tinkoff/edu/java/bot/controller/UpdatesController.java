package ru.tinkoff.edu.java.bot.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.natishark.course.tinkoff.bot.dto.LinkUpdateRequest;

@RestController
public class UpdatesController {

    @PostMapping("api/updates")
    public void sendUpdate(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
    }
}
