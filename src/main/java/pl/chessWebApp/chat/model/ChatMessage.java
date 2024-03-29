package pl.chessWebApp.chat.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ChatMessage {

    private String value;
    private String user;
    private String userColor;
    private String localDateTime = localDateTimeToString(Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime());

    private String localDateTimeToString(LocalDateTime accountCreationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return accountCreationDate.format(formatter);
    }

    public ChatMessage() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ChatMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

}
