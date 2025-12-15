package com.wf.agent.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynthesisAgent {

    private final ChatClient chatClient;

    // Spring AI automatically injects a configured ChatClient instance
    public SynthesisAgent(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String synthesizeAnswer(String userQuery, String retrievedContext) {
        System.out.println("Synthesis Agent: Calling Gemini with Context...");

        // 🟢 Agent Logic: Define the AI's persona and instructions (The System Prompt)
        SystemMessage systemMessage = new SystemMessage(
            "You are the Wells Fargo Compliance Officer AI. Your sole job is to answer the user's " +
            "query ONLY based on the provided CONTEXT. Do not use outside knowledge. " +
            "Start your response with '✅ Result: Compliance confirmed.' if the context supports it, and " +
            "always include the policy source in a '📖 Source Policy:' line."
        );

        // Combine the context and the user's question into the final prompt
        String fullPrompt = retrievedContext + "\n\nUser Question: " + userQuery;
        
        // Use the ChatClient to execute the RAG-style prompt
        String finalAnswer = chatClient.prompt()
            .messages(List.of(systemMessage)) // Inject the System Prompt
            .user(fullPrompt)
            .call()
            .content();

        return finalAnswer;
    }
}