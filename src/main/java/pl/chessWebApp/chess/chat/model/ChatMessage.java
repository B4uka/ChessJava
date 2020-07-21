package pl.chessWebApp.chess.chat.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ChatMessage {

    private String value;
    private String user;
    private String userColor;
    private String localDateTime = localDateTimeToString(Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime());

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

    private String localDateTimeToString(LocalDateTime accountCreationDate) {

        // Custom format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Format LocalDateTime
        return accountCreationDate.format(formatter);
    }
}
