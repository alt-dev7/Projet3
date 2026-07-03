package com.chatop.message;

import com.chatop.common.dto.SimpleMessageResponse;
import com.chatop.message.dto.MessageRequest;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public SimpleMessageResponse send(MessageRequest request) {
        Message message = new Message();
        message.setRentalId(request.getRentalId());
        message.setUserId(request.getUserId());
        message.setMessage(request.getMessage());
        messageRepository.save(message);

        return new SimpleMessageResponse("Message send with success");
    }
}
