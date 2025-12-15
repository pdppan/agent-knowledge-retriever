package com.wf.agent.service;

import org.springframework.stereotype.Service;

@Service
public class TriageAgent {

    public String triageQuery(String query) {
        // 🟢 Agent Logic: In a real app, this would use Gemini to classify the input
        // (e.g., Compliance, HR, IT) to pull contextually relevant policy documents.
        
        // For the demo, we simulate the triage result (always Compliance).
        System.out.println("Triage Agent: Classified query as 'Compliance Access'.");
        return "Compliance Access Policy"; 
    }
}