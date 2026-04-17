package com.tourme.controllers;

import com.tourme.dto.ApiResponse;
import com.tourme.models.Message;
import com.tourme.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Send a message from one user to another
     * 
     * @param senderId    - The ID of the user sending the message
     * @param receiverId  - The ID of the user receiving the message
     * @param messageData - JSON object containing "content" field
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam int senderId, @RequestParam int receiverId,
            @RequestBody Map<String, String> messageData) {
        try {
            String content = messageData.get("content");
            return messageService.sendMessage(senderId, receiverId, content);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to send message: " + e.getMessage());
        }
    }

    /**
     * Get all messages between two users (in both directions)
     * 
     * @param userId1 - First user ID
     * @param userId2 - Second user ID
     */
    @GetMapping("/conversation")
    public ResponseEntity<?> getMessages(@RequestParam int userId1, @RequestParam int userId2) {
        try {
            return messageService.getMessages(userId1, userId2);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve messages: " + e.getMessage());
        }
    }

    /**
     * Get all incoming messages for a user
     * 
     * @param userId - The user ID
     */
    @GetMapping("/incoming/{userId}")
    public ResponseEntity<?> getIncomingMessages(@PathVariable int userId) {
        try {
            return messageService.getIncomingMessages(userId);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve incoming messages: " + e.getMessage());
        }
    }
}
