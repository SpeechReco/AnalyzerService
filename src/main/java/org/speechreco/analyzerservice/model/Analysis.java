package org.speechreco.analyzerservice.model;

import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity(name = "analysis")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "recordingid")
    private int recordingID;

    @Column(name = "title")
    private String title;

    @Column(name = "texturi")
    private String textURI;

    @Column(name = "creationdate")
    private Timestamp creationDate;

    @Transient
    private String transcription;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void validateTranscript() {
        if (transcription == null) return;
    }
}
