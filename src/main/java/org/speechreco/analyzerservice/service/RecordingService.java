package org.speechreco.analyzerservice.service;

import com.google.cloud.storage.Bucket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.speechreco.analyzerservice.dao.RecordingRepository;
import org.speechreco.analyzerservice.model.Recording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class RecordingService {
    @Autowired
    private RecordingRepository recordingRepository;
    @Autowired
    private Bucket storageBucket;

    public boolean saveRecording(long uid, String data) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Recording recording = gson.fromJson(data, Recording.class);
        if (uid != recording.getUserID()) return false;
        storageBucket.create(String.valueOf(recording.getId()), recording.getAudioBytes());
        System.out.println(Arrays.toString(recording.getAudioBytes()));
        try {
            String url = String.valueOf(storageBucket.get(String.valueOf(recording.getId())).signUrl(10000000, TimeUnit.SECONDS));
            recording.setRecordingURI(url);
            recordingRepository.save(recording);
            return true;
        } catch (Exception e) {
            storageBucket.delete(Bucket.BucketSourceOption.userProject(String.valueOf(recording.getId())));
            return false;
        }
    }

    public Stream<Recording> getRecordingsByUid(long uid) {
        return recordingRepository.getByUserID(uid).stream();
    }

    public Recording getSpecificRecording(int userId, long audioId) {
        Recording retrievedRecording = recordingRepository.findById(audioId).orElse(null);
        if (retrievedRecording != null && retrievedRecording.getUserID() == userId) {
            return retrievedRecording;
        }
        return null;
    }

    public boolean deleteRecording(int userId, int audioId) {
        try {
            recordingRepository.delete(getSpecificRecording(userId, audioId));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
