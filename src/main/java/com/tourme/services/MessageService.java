package com.tourme.services;

import com.tourme.dto.ApiResponse;
import com.tourme.models.Message;
import com.tourme.models.User;
import com.tourme.repositories.MessageRepository;
import com.tourme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Send a message from one user to another
     */
    public ResponseEntity<?> sendMessage(int senderId, int receiverId, String content) {
        try {
            // Check if sender exists
            Optional<User> senderOpt = userRepository.findById(senderId);
            if (!senderOpt.isPresent()) {
                return ApiResponse.notFound("Sender not found");
            }

            // Check if receiver exists
            Optional<User> receiverOpt = userRepository.findById(receiverId);
            if (!receiverOpt.isPresent()) {
                return ApiResponse.notFound("Receiver not found");
            }

            // Validate message content
            if (content == null || content.trim().isEmpty()) {
                return ApiResponse.badRequest("Message content cannot be empty");
            }

            // Create and save message
            Message message = new Message(senderOpt.get(), receiverOpt.get(), content);
            Message savedMessage = messageRepository.save(message);

            return ApiResponse.created("Message sent successfully", savedMessage);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to send message: " + e.getMessage());
        }
    }

    /**
     * Get all messages between two users
     */
    public ResponseEntity<?> getMessages(int userId1, int userId2) {
        try {
            // Check if both users exist
            if (!userRepository.findById(userId1).isPresent()) {
                return ApiResponse.notFound("User " + userId1 + " not found");
            }

            if (!userRepository.findById(userId2).isPresent()) {
                return ApiResponse.notFound("User " + userId2 + " not found");
            }

            // Get messages between the two users (both directions)
            List<Message> messages = messageRepository
                    .findBySenderUserIdAndReceiverUserIdOrSenderUserIdAndReceiverUserIdOrderByTimestampDesc(
                            userId1, userId2, userId2, userId1);

            return ApiResponse.ok("Messages retrieved successfully", messages);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve messages: " + e.getMessage());
        }
    }

    /**
     * Get all incoming messages for a user
     */
    public ResponseEntity<?> getIncomingMessages(int userId) {
        try {
            // Check if user exists
            if (!userRepository.findById(userId).isPresent()) {
                return ApiResponse.notFound("User not found");
            }

            // Get all messages where user is the receiver
            List<Message> messages = messageRepository.findAll().stream()
                    .filter(m -> m.getReceiver().getUserId() == userId)
                    .sorted((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()))
                    .toList();

            return ApiResponse.ok("Incoming messages retrieved successfully", messages);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to retrieve incoming messages: " + e.getMessage());
        }
    }
}
