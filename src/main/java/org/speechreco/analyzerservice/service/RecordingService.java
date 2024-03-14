package org.speechreco.analyzerservice.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.speechreco.analyzerservice.dao.RecordingRepository;
import org.speechreco.analyzerservice.model.Recording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.Stream;

@Service
public class RecordingService {
    @Autowired
    private RecordingRepository recordingRepository;

    public boolean saveRecording(long uid, String data) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Recording recording = gson.fromJson(data, Recording.class);
        if (uid != recording.getUserID()) return false;
        try {
            recordingRepository.save(recording);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Stream<Recording> getRecordingsByUid(long uid) {
        return recordingRepository.getByUserID(uid).stream();
    }

    public Recording getSpecificRecording(int userId, long audioId) {
        return recordingRepository.findById(audioId).orElse(null);
    }

    public boolean deleteRecording(int userId, int audioId) {
        recordingRepository.delete(getSpecificRecording(userId, audioId));
        return true;
    }
}
