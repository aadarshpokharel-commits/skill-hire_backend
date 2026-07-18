package com.skillhire.backend.controller;

import com.skillhire.backend.dto.message.MessageRequest;
import com.skillhire.backend.dto.message.MessageResponse;
import com.skillhire.backend.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> send(@Valid @RequestBody MessageRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.send(req));
    }

    // GET /api/messages/conversation?userA=1&userB=2
    @GetMapping("/conversation")
    public List<MessageResponse> conversation(@RequestParam Long userA, @RequestParam Long userB) {
        return messageService.conversation(userA, userB);
    }
}
