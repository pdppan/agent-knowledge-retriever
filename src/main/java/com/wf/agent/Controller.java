package com.wf.agent;

import com.wf.agent.service.TriageAgent;
import com.wf.agent.service.RetrievalAgent;
import com.wf.agent.service.SynthesisAgent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final TriageAgent triageAgent;
    private final RetrievalAgent retrievalAgent;
    private final SynthesisAgent synthesisAgent;

    // Inject all three agent services via the constructor
    public Controller(TriageAgent triageAgent, RetrievalAgent retrievalAgent, SynthesisAgent synthesisAgent) {
        this.triageAgent = triageAgent;
        this.retrievalAgent = retrievalAgent;
        this.synthesisAgent = synthesisAgent;
    }

	@GetMapping("/")
    public String healthCheck() {
        return "Agent is running. Use the /query endpoint to ask a question.";
    }

    @GetMapping("/query") // Use /query for a cleaner REST endpoint
    public String getKnowledgeAnswer(@RequestParam(defaultValue = "Can Customer Support view PII data?") String query) {
        System.out.println("\n--- Starting Agent Workflow ---");

        // 1. Triage Agent: Route the query
        String classifiedTopic = triageAgent.triageQuery(query);

        // 2. Retrieval Agent: Fetch the policy chunks
        String retrievedContext = retrievalAgent.retrievePolicyChunks(classifiedTopic);

        // 3. Synthesis Agent: Generate the final, grounded answer using Gemini
        String finalAnswer = synthesisAgent.synthesizeAnswer(query, retrievedContext);

        System.out.println("--- Workflow Complete ---\n");
        return "WF Agentic Knowledge Retriever running on Cloud Run.\n\n" +
               "User Query: " + query + "\n" +
               "--------------------------------------------------\n" +
               finalAnswer;
    }
}