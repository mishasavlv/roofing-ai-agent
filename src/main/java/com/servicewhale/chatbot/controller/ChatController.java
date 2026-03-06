package com.servicewhale.chatbot.controller;

import com.servicewhale.chatbot.model.Message;
import com.servicewhale.chatbot.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        System.out.println("[ChatController] Initializing ChatController...");
        this.chatService = chatService;
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> sendMessage(@RequestBody Message message) {
        System.out.println("[ChatController] sendMessage endpoint called with message: " + message.getContent() + " for threadId: " + message.getThreadId());
        return chatService.sendMessage(message.getThreadId(), message.getContent())
                .timeout(java.time.Duration.ofSeconds(24))
                .onErrorReturn(e -> e instanceof java.util.concurrent.TimeoutException, "{\"response\":\"threadDeadError\"}");
    }
    
    @GetMapping(value = "/listKeys")
    public String listRedisKeys() {
    	return chatService.redisServiceKeys();
    }
    
    @PostMapping(value = "/getValue")
    public String getRedisValue(@RequestBody Map<String,String> payload) {
    	return chatService.redisServiceGetValue(payload.get("key"));
    }

    @GetMapping("/generateThreadId")
    public Mono<Map<String, String>> generateThreadId() {
        System.out.println("[ChatController] generateThreadId endpoint called.");
        return chatService.generateThreadId()
                .map(threadId -> {
                    System.out.println("[ChatController] Generated threadId: " + threadId);
                    return Map.of("threadId", threadId);
                });
    }

    @PostMapping("/close")
    public void closeConversation(@RequestBody Map<String, String> payload) {
        String threadId = payload.get("threadId");
        System.out.println("[ChatController] closeConversation endpoint called with threadId: " + threadId);
        chatService.closeConversation(threadId);
    }
}
