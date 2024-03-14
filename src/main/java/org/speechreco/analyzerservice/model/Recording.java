package org.speechreco.analyzerservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
