package com.vlsi.chatbot.dto;

public class ChatResponse {
    
    private String response;
    private int matchScore;
    private boolean success;
    
    public ChatResponse() {
        this.success = true;
    }
    
    public ChatResponse(String response) {
        this.response = response;
        this.matchScore = 0;
        this.success = true;
    }
    
    public ChatResponse(String response, int matchScore) {
        this.response = response;
        this.matchScore = matchScore;
        this.success = true;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public int getMatchScore() {
        return matchScore;
    }
    
    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
