package org.speechreco.analyzerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
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
}
