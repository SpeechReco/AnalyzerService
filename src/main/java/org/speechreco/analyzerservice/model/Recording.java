package org.speechreco.analyzerservice.model;


import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userID;
    private String title;
    private String recordingURI;
    private Timestamp creationDate;

    @Transient
    private byte[] audioBytes;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
