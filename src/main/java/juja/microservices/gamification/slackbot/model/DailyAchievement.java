package juja.microservices.gamification.slackbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailyAchievement {
    @JsonProperty
    private String from;
    @JsonProperty
    private String description;

    public DailyAchievement (String from, String description) {
        this.from = from;
        this.description = description;
    }
}
