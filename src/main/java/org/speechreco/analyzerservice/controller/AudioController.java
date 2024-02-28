package org.speechreco.analyzerservice.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audios")
public class AudioController {

    @GetMapping("/{userId}")
    public String getAudiosForUser(@PathVariable int userId){
        return "Get all audios for user " + userId;
    }
    @PostMapping("/{userId}")
    public String postAudiosForUser(@PathVariable int userId){
        return "Post an audio for user " + userId;
    }

    @GetMapping("/{userId}/{audioId}")
    public String getSpecificAudioForUser(@PathVariable int userId, @PathVariable int audioId){
        return "Get audio " + audioId + " for user " + userId;
    }

    @PostMapping("/{userId}/{audioId}")
    public String postAudioForTranscription(@PathVariable int userId, @PathVariable int audioId){
        return "Post an audio" + audioId + " for user " + userId + " to transcription";
    }

    @DeleteMapping("/{userId}/{audioId}")
    public String deleteSpecificAudio(@PathVariable int userId, @PathVariable int audioId){
        return "Delete an audio " + audioId + " of user " + userId;
    }




}
