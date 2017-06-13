package juja.microservices.gamification.slackbot.service.impl;

import juja.microservices.gamification.slackbot.exceptions.UserExchangeException;
import juja.microservices.gamification.slackbot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nikolay Horushko
 */

@Service
public class SlackNameHandlerService {

    private UserService userService;
    @Value("${parsedUuid.startMarker}")
    private String parsedUuidStartMarker;
    @Value("${parsedUuid.finishMarker}")
    private String parsedUuidFinishMarker;
    /**
     * Slack name cannot be longer than 21 characters and
     * can only contain letters, numbers, periods, hyphens, and underscores.
     * ([a-z0-9\.\_\-]){1,21}
     * quick test regExp http://regexr.com/
     */
    private final String SLACK_NAME_PATTERN = "@([a-zA-z0-9\\.\\_\\-]){1,21}";

    @Inject
    public SlackNameHandlerService(UserService userService) {
        this.userService = userService;
    }

    public String replaceSlackNamesToUuids(String text) {
        if (text == null) {
            return "";
        }
        Pattern pattern = Pattern.compile(SLACK_NAME_PATTERN);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            try {
                String slackName = matcher.group();
                String uuid = userService.findUuidUserBySlack(slackName.toLowerCase());
                text = text.replaceAll(slackName, parsedUuidStartMarker + uuid + parsedUuidFinishMarker);
            } catch (UserExchangeException ex) {
                //
            }
        }
        return text;
    }
}

