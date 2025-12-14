package com.vlsi.chatbot.repository;

import com.vlsi.chatbot.entity.ChatMessage;
import com.vlsi.chatbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    List<ChatMessage> findByUserOrderByCreatedAtDesc(User user);
    
    List<ChatMessage> findTop50ByUserOrderByCreatedAtDesc(User user);
}
