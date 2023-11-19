package pgdp.messenger;

import java.time.*;

public class Message {
    private long id;
    private LocalDateTime timestamp;
    private User author;
    private String content;

    public Message(long id, LocalDateTime timestamp, User author, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.author = author;
        this.content = content;
    }

    /** Gibt eine String-Repräsentation dieses Message-Objektes zurück.
     *  Die Form ist "ID:Author-ID:Time-Stamp:Inhalt"
     * @return Die String-Repräsentation dieser Message
     */
    public String toString() {
        String authorString = "No Author Available";
        if(author != null) {
            authorString = author.getId() + "";
        }
        String timestampString = "No Time Available";
        if(timestamp != null) {
            timestampString = timestamp.toString();
        }

        return id + "; " + authorString + "; "
                + timestampString + ": " + content;
    }

    /* ================ Getter und Setter ================ */

    public long getId() {
        return id;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public User getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}