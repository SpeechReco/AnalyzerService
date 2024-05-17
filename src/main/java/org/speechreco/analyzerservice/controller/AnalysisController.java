package org.speechreco.analyzerservice.controller;

import org.apache.logging.log4j.message.Message;
import org.speechreco.analyzerservice.model.Analysis;
import org.speechreco.analyzerservice.model.AnalysisRequest;
import org.speechreco.analyzerservice.model.Recording;
import org.speechreco.analyzerservice.service.AnalysisService;
import org.speechreco.analyzerservice.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/analyses")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private MessagingService messagingService;

    @GetMapping("/{userId}/{audioId}")
    public Stream<Analysis> getAudiosForUser(@PathVariable("userId") int userId, @PathVariable("audioId") int audioId) {
        return analysisService.getAllAnalysesForRecording(userId, audioId);
    }

    // A concurrent map to store the futures by analysisId
    private final ConcurrentHashMap<Integer, CompletableFuture<String>> futureMap = new ConcurrentHashMap<>();


    @PostMapping("/{userId}/{audioId}/{analysisId}")
    public DeferredResult<ResponseEntity<String>> postAnalysisToGPT(@PathVariable("userId") int userId,
                                                                    @PathVariable("audioId") int audioId,
                                                                    @PathVariable("analysisId") int analysisId,
                                                                    @RequestBody AnalysisRequest request) {

        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();

        if (messagingService.sendToGPT(userId, audioId, analysisId, request.getText(), request.getPrompt())) {
            CompletableFuture<String> future = new CompletableFuture<>();
            futureMap.put(analysisId, future);

            // When the future is completed, set the result on the deferredResult
            future.thenAccept(result -> {
                deferredResult.setResult(ResponseEntity.ok(result));
            }).exceptionally(ex -> {
                deferredResult.setErrorResult(ResponseEntity.status(500).body("Error processing analysis"));
                return null;
            });

        } else {
            deferredResult.setResult(ResponseEntity.status(405).build());
        }

        return deferredResult;
    }

    // Method to be called by the listener when the background process completes
    public void completeAnalysis(int analysisId, String result) {
        CompletableFuture<String> future = futureMap.remove(analysisId);
        if (future != null) {
            future.complete(result);
        }
    }

}
