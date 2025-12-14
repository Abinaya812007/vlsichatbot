package com.vlsi.chatbot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "qa_entries")
public class QAEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(nullable = false, length = 1000)
    private String question;
    
    @Column(nullable = false, length = 5000)
    private String answer;
    
    @Column(name = "category")
    private String category;
    
    public QAEntry() {}
    
    public QAEntry(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    
    public QAEntry(String question, String answer, String category) {
        this.question = question;
        this.answer = answer;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}
