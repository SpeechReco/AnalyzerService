package org.speechreco.analyzerservice.model;

import com.google.gson.Gson;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GPTMessagePacket {
    private int uid;
    private int rid;
    private int aid;
    private String text;
    private String prompt;
    private String response;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
