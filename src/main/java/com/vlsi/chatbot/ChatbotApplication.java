package com.vlsi.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatbotApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ChatbotApplication.class, args);
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║       🌸 VLSI Chatbot Started Successfully! 🌸        ║");
        System.out.println("║                                                       ║");
        System.out.println("║   Open your browser and go to:                        ║");
        System.out.println("║   👉 http://localhost:8080                            ║");
        System.out.println("║                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }
}
