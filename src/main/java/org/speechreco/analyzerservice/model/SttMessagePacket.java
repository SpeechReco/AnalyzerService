package org.speechreco.analyzerservice.model;

import com.google.gson.Gson;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SttMessagePacket {
    private String analysisName;
    private String language;
    private int speakerAmount;
    private boolean generateSummary;
    private Recording recording;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
