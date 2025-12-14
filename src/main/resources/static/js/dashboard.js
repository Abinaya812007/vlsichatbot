// Dashboard JavaScript
document.addEventListener('DOMContentLoaded', function() {
    const chatForm = document.getElementById('chatForm');
    const messageInput = document.getElementById('messageInput');
    const sendBtn = document.getElementById('sendBtn');
    const messagesContainer = document.getElementById('messagesContainer');
    const welcomeScreen = document.getElementById('welcomeScreen');
    const chatContainer = document.getElementById('chatContainer');
    
    // Auto-resize textarea
    messageInput.addEventListener('input', function() {
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 200) + 'px';
        
        // Enable/disable send button
        sendBtn.disabled = !this.value.trim();
    });
    
    // Handle Enter key (Shift+Enter for new line)
    messageInput.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            if (this.value.trim()) {
                chatForm.dispatchEvent(new Event('submit'));
            }
        }
    });
    
    // Handle form submission
    chatForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const message = messageInput.value.trim();
        if (!message) return;
        
        // Hide welcome screen and show messages
        if (welcomeScreen.style.display !== 'none') {
            welcomeScreen.style.display = 'none';
            messagesContainer.classList.add('active');
        }
        
        // Add user message
        addMessage('user', message);
        
        // Clear input
        messageInput.value = '';
        messageInput.style.height = 'auto';
        sendBtn.disabled = true;
        
        // Show typing indicator
        const typingId = showTypingIndicator();
        
        try {
            const response = await fetch('/api/chat', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ message: message })
            });
            
            // Remove typing indicator
            removeTypingIndicator(typingId);
            
            if (response.ok) {
                const data = await response.json();
                addMessage('assistant', data.response);
            } else if (response.status === 401 || response.status === 403) {
                addMessage('assistant', '⚠️ Session expired. Redirecting to login...');
                setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
            } else {
                addMessage('assistant', '❌ Sorry, something went wrong. Please try again.');
            }
        } catch (error) {
            removeTypingIndicator(typingId);
            console.error('Chat error:', error);
            addMessage('assistant', '❌ Connection error. Please check your network and try again.');
        }
        
        messageInput.focus();
    });
    
    // Add message to chat
    function addMessage(role, content) {
        const messageDiv = document.createElement('div');
        messageDiv.className = `message ${role}`;
        
        const avatarText = role === 'user' ? 'You' : 'AI';
        const formattedContent = formatMessage(content);
        
        messageDiv.innerHTML = `
            <div class="message-avatar">${avatarText.charAt(0)}</div>
            <div class="message-content">${formattedContent}</div>
        `;
        
        messagesContainer.appendChild(messageDiv);
        scrollToBottom();
    }
    
    // Format message content
    function formatMessage(content) {
        // Convert newlines to paragraphs
        const paragraphs = content.split('\n\n');
        let formatted = '';
        
        paragraphs.forEach(para => {
            if (para.trim()) {
                // Check for lists
                if (para.includes('\n•') || para.includes('\n-') || para.includes('\n*')) {
                    const lines = para.split('\n');
                    let listItems = '';
                    let introText = '';
                    
                    lines.forEach((line, idx) => {
                        if (line.match(/^[•\-\*]\s/)) {
                            listItems += `<li>${line.replace(/^[•\-\*]\s/, '')}</li>`;
                        } else if (listItems === '') {
                            introText += line + ' ';
                        }
                    });
                    
                    if (introText) {
                        formatted += `<p>${introText.trim()}</p>`;
                    }
                    if (listItems) {
                        formatted += `<ul>${listItems}</ul>`;
                    }
                } else {
                    // Regular paragraph - handle single newlines
                    formatted += `<p>${para.replace(/\n/g, '<br>')}</p>`;
                }
            }
        });
        
        return formatted || `<p>${content}</p>`;
    }
    
    // Show typing indicator
    function showTypingIndicator() {
        const id = 'typing-' + Date.now();
        const typingDiv = document.createElement('div');
        typingDiv.className = 'message assistant';
        typingDiv.id = id;
        typingDiv.innerHTML = `
            <div class="message-avatar">A</div>
            <div class="message-content">
                <div class="typing-indicator">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        `;
        messagesContainer.appendChild(typingDiv);
        scrollToBottom();
        return id;
    }
    
    // Remove typing indicator
    function removeTypingIndicator(id) {
        const element = document.getElementById(id);
        if (element) {
            element.remove();
        }
    }
    
    // Scroll to bottom
    function scrollToBottom() {
        chatContainer.scrollTop = chatContainer.scrollHeight;
    }
    
    // Focus input on load
    messageInput.focus();
});

// Send suggestion
function sendSuggestion(text) {
    const messageInput = document.getElementById('messageInput');
    messageInput.value = text;
    messageInput.dispatchEvent(new Event('input'));
    document.getElementById('chatForm').dispatchEvent(new Event('submit'));
}

// Toggle sidebar (mobile)
function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('open');
}

// New chat
function newChat() {
    const messagesContainer = document.getElementById('messagesContainer');
    const welcomeScreen = document.getElementById('welcomeScreen');
    
    messagesContainer.innerHTML = '';
    messagesContainer.classList.remove('active');
    welcomeScreen.style.display = 'flex';
    
    document.getElementById('messageInput').focus();
}
