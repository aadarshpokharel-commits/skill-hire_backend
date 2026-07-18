package com.skillhire.backend.service;

import com.skillhire.backend.dto.message.MessageRequest;
import com.skillhire.backend.dto.message.MessageResponse;
import com.skillhire.backend.entity.Message;
import com.skillhire.backend.entity.User;
import com.skillhire.backend.exception.ResourceNotFoundException;
import com.skillhire.backend.repository.MessageRepository;
import com.skillhire.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Transactional
    public MessageResponse send(MessageRequest req) {
        User sender = userRepository.findById(req.senderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found: " + req.senderId()));
        User receiver = userRepository.findById(req.receiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found: " + req.receiverId()));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(req.content())
                .build();

        return mapper.toMessageResponse(messageRepository.save(message));
    }

    public List<MessageResponse> conversation(Long userA, Long userB) {
        return messageRepository.findConversation(userA, userB)
                .stream().map(mapper::toMessageResponse).toList();
    }
}
