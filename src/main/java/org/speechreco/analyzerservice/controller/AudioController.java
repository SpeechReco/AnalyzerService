package org.speechreco.analyzerservice.controller;


import org.speechreco.analyzerservice.model.Recording;
import org.speechreco.analyzerservice.service.MessagingService;
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

    @Autowired
    private MessagingService messagingService;

    @GetMapping("/{userId}")
    public Stream<Recording> getAudiosForUser(@PathVariable("userId") long userId) {
        return recordingService.getRecordingsByUid(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> postAudiosForUser(@PathVariable("userId") int userId, @RequestBody String body) {
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
    public ResponseEntity<String> postAudioForTranscription(@PathVariable("userId") int userId, @PathVariable("audioId") int audioId) {
        Recording recordingToText = recordingService.getSpecificRecording(userId, audioId);
        if (recordingToText != null && recordingToText.getUserID() == userId) {
            if (messagingService.send(recordingToText)) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(200));
            }
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(405));
    }

    @DeleteMapping("/{userId}/{audioId}")
    public ResponseEntity<String> deleteSpecificAudio(@PathVariable int userId, @PathVariable int audioId) {
        if (recordingService.deleteRecording(userId, audioId)) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(402));
    }

}
