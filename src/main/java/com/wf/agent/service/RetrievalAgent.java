package com.wf.agent.service;

import org.springframework.stereotype.Service;

@Service
public class RetrievalAgent {

    public String retrievePolicyChunks(String classifiedTopic) {
        // 🟢 Agent Logic: This is where the code would call the Vertex AI Search API 
        // using the topic determined by the TriageAgent to retrieve policy documents.

        // For the demo, we return the synthetic, grounded context.
        System.out.println("Retrieval Agent: Fetching documents based on: " + classifiedTopic);
        
        String policyChunk1 = "POLICY: PII Data Access 4.2. Support roles are strictly prohibited from writing or deleting PII data. Read-only access is permitted solely for incident resolution, subject to two-factor authentication.";
        String policyChunk2 = "INCIDENT LOG: 2024-08-15. Support team confirmed read-only access enabled for role ID 3302. No write access granted.";
        
        return "CONTEXT FOR LLM:\n" + policyChunk1 + "\n" + policyChunk2;
    }
}