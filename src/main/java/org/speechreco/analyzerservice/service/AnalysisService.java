package org.speechreco.analyzerservice.service;

import com.google.cloud.storage.Bucket;
import org.speechreco.analyzerservice.dao.AnalysisRepository;
import org.speechreco.analyzerservice.model.Analysis;
import org.speechreco.analyzerservice.model.GPTMessagePacket;
import org.speechreco.analyzerservice.model.Recording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class AnalysisService {
    @Autowired
    private AnalysisRepository analysisRepository;
    @Autowired
    private Bucket storageBucket;

    public boolean save(Analysis response) {
        try {
            Analysis savedAnalysis = analysisRepository.save(response);
            savedAnalysis.validateTranscript();
            storageBucket.create("analysis/"+savedAnalysis.getRecordingID() + "/" + savedAnalysis.getId(), savedAnalysis.getTranscription().getBytes());
            String url = String.valueOf(storageBucket.get("analysis/"+savedAnalysis.getRecordingID() + "/" + savedAnalysis.getId()).signUrl(10000000, TimeUnit.SECONDS));
            savedAnalysis.setTextURI(url);
            analysisRepository.updateUrlById(savedAnalysis.getId(),url);
            return true;
        } catch (Exception e) {
//            storageBucket.delete(Bucket.BucketSourceOption.userProject(String.valueOf(savedAnalysis.getRecordingID() + "/" + savedAnalysis.getId())));
            return false;
        }
    }

    public Stream<Analysis> getAllAnalysesForRecording(int userId, int audioId) {
        List<Analysis> analysisList = analysisRepository.getByRecordingID(audioId);
        return analysisList.stream();
    }

}
