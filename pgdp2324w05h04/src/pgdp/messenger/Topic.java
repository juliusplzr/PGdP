package pgdp.messenger;

import java.time.*;

public class Topic {
    private long id;
    private String name;

    private List messages;
    private List pinnedMessages;

    private static long messageID;

    public Topic(long id, String name) {
        this.id = id;
        this.name = name;
        this.messages = new List();
        this.pinnedMessages = new List();
    }

    /** Fügt den Messages dieses Topics eine neue Message zum übergebenen Zeitpunkt, vom übergebenen Autor
     *  und mit dem übergebenen Inhalt hinzu.
     * @param timestamp Ein beliebiges LocalDateTime-Objekt
     * @param author Ein beliebiges Author-Objekt
     * @param content Ein beliebiger String
     */
    public void writeMessage(LocalDateTime timestamp, User author, String content) {
        messages.add(new Message(messageID++, timestamp, author, content));
    }

    /** Fügt die Nachricht mit der übergebenen ID den Pinned Messages hinzu.
     *  Wenn die übergebene ID nicht in der Liste von Nachrichten enthalten ist, geschieht nichts.
     * @param id Ein beliebiger long
     */
    public void pinMessage(long id) {
        Message message = messages.getByID(id);
        if(message != null) {
            pinnedMessages.add(message);
        }
    }

    /** Entfernt die Nachricht mit der übergebenen ID aus sowohl der Liste von normalen Nachrichten,
     * 	als auch der Liste der Pinned Messages und gibt das entfernte Message-Objekt zurück.
     * 	Wenn nichts entfernt wurde, wird 'null' zurückgegeben.
     * @param id Ein beliebiger long
     * @return Die entfernte Message oder 'null'
     */
    public Message deleteMessage(long id) {
        Message message = messages.getByID(id);
        messages.delete(message);
        pinnedMessages.delete(message);
        return message;
    }

    /** Setzt den Inhalt einer Message mit der übergebenen ID auf den übergebenen neuen Inhalt.
     *  Wenn keine Message mit der übergebenen ID in der Liste von Messages dieses Topics existiert, geschieht nichts.
     *  Der Rückgabewert signalisiert, ob eine Message mit der übergebenen ID gefunden wurde.
     * @param id Ein beliebiger long
     * @param newContent Ein beliebiger String
     * @return true gdw. ein Message-Objekt mit der übergebenen ID gefunden wurde
     */
    public boolean editMessage(long id, String newContent) {
        Message m = messages.getByID(id);
        if (m != null) {
            m.setContent(newContent);
        }
        return m != null;
    }

    /** Gibt eine String-Repräsentation dieses Topics mit allen Messages zurück.
     * @return String-Repräsentation des Topics
     */
    public String toString() {
        return "Topic '" + name + "':\n" + messages.toString();
    }

    /** Gibt eine String-Repräsentation dieses Topics mit allen Pinned Messages zurück.
     * @return String-Repräsentation des Topics
     */
    public String pinnedToString() {
        return "Pinned messages in Topic '" + name + "':\n" + pinnedMessages.toString();
    }

    /* ================ Getter und Setter ================ */

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List getMessages() {
        return messages;
    }
    public List getPinnedMessages() {
        return pinnedMessages;
    }

    public void setName(String name) {
        this.name = name;
    }
}