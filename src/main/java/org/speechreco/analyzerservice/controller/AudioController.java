package org.speechreco.analyzerservice.controller;


import org.speechreco.analyzerservice.model.Recording;
import org.speechreco.analyzerservice.service.RecordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/audios")
public class AudioController {
    @Autowired
    private RecordingService recordingService;

    @GetMapping("/{userId}")
    public Stream<Recording> getAudiosForUser(@PathVariable long userId) {
        return recordingService.getRecordingsByUid(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> postAudiosForUser(@PathVariable int userId, @RequestBody String body) {
        if (recordingService.saveRecording(userId, body)) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(402));
    }

    @GetMapping("/{userId}/{audioId}")
    public Recording getSpecificAudioForUser(@PathVariable int userId, @PathVariable int audioId) {
        return recordingService.getSpecificRecording(userId, audioId);
    }

    @PostMapping("/{userId}/{audioId}")
    public String postAudioForTranscription(@PathVariable int userId, @PathVariable int audioId) {
        return "Post an audio" + audioId + " for user " + userId + " to transcription";
    }

    @DeleteMapping("/{userId}/{audioId}")
    public String deleteSpecificAudio(@PathVariable int userId, @PathVariable int audioId) {
        return "Delete an audio " + audioId + " of user " + userId;
    }


}
