package com.vlsi.chatbot.controller;

import com.vlsi.chatbot.dto.ChatRequest;
import com.vlsi.chatbot.dto.ChatResponse;
import com.vlsi.chatbot.entity.User;
import com.vlsi.chatbot.service.ChatService;
import com.vlsi.chatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @RequestBody ChatRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        User user = null;
        if (userDetails != null) {
            user = userService.findByUsername(userDetails.getUsername()).orElse(null);
        }
        
        ChatResponse response = chatService.processMessage(request.getMessage(), user);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/qa-count")
    public ResponseEntity<Long> getQACount() {
        return ResponseEntity.ok(chatService.getQACount());
    }
}
