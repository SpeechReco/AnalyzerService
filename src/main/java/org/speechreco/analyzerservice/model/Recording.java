package org.speechreco.analyzerservice.model;


import com.google.gson.Gson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
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
