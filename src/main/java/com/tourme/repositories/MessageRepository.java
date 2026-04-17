package com.tourme.repositories;

import com.tourme.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Find all messages between two users (in both directions)
     */
    List<Message> findBySenderUserIdAndReceiverUserIdOrSenderUserIdAndReceiverUserIdOrderByTimestampDesc(
            int senderId, int receiverId, int senderId2, int receiverId2);

    /**
     * Find all messages from a specific sender to a specific receiver
     */
    List<Message> findBySenderUserIdAndReceiverUserIdOrderByTimestampDesc(int senderId, int receiverId);
}
