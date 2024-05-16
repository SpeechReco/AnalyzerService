package org.speechreco.analyzerservice.controller;

import org.speechreco.analyzerservice.model.Analysis;
import org.speechreco.analyzerservice.model.Recording;
import org.speechreco.analyzerservice.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/analyses")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/{userId}/{audioId}")
    public Stream<Analysis> getAudiosForUser(@PathVariable("userId") int userId, @PathVariable("audioId") int audioId) {
        return analysisService.getAllAnalysesForRecording(userId, audioId);
    }

}
