package com.vlsi.chatbot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "user_message", nullable = false, length = 2000)
    private String userMessage;
    
    @Column(name = "bot_response", nullable = false, length = 5000)
    private String botResponse;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "match_score")
    private Integer matchScore;
    
    public ChatMessage() {
        this.createdAt = LocalDateTime.now();
    }
    
    public ChatMessage(User user, String userMessage, String botResponse, Integer matchScore) {
        this.user = user;
        this.userMessage = userMessage;
        this.botResponse = botResponse;
        this.matchScore = matchScore;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getUserMessage() {
        return userMessage;
    }
    
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
    
    public String getBotResponse() {
        return botResponse;
    }
    
    public void setBotResponse(String botResponse) {
        this.botResponse = botResponse;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Integer getMatchScore() {
        return matchScore;
    }
    
    public void setMatchScore(Integer matchScore) {
        this.matchScore = matchScore;
    }
}
