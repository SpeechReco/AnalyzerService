package org.speechreco.analyzerservice.service;

import org.speechreco.analyzerservice.dao.AnalysisRepository;
import org.speechreco.analyzerservice.dao.RecordingRepository;
import org.speechreco.analyzerservice.model.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    @Autowired
    private AnalysisRepository analysisRepository;

    public void save(Analysis response) {
        analysisRepository.save(response);
    }
}
