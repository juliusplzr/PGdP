package pgdp.messenger;

public class PinguTalk {
	// TODO: Implementiere die fehlenden Attribute
    private static long topicID;
    private static long userID;
    private Topic[] topics;
    private UserArray members;

    // TODO: Implementiere den fehlenden Konstruktor
    public PinguTalk(int length, int size) {
        this.topics = size > 1 ? new Topic[size] : new Topic[1];
        this.members = length > 1 ? new UserArray(length) : new UserArray(1);
    }

    // TODO: Implementiere die fehlenden Methoden

    public User addMember(String name, User supervisor) {
        User user = new User(userID++, name, supervisor);
        members.addUser(user);
        return user;
    }

    public User deleteMember(long id) {
        return members.deleteUser(id);
    }

    public Topic createNewTopic(String name) {
        Topic topicNew = new Topic(topicID++, name);

        for (int i = 0; i < topics.length; i++) {
            if (topics[i] == null) {
                topics[i] = topicNew;
                return topicNew;
            }
        }

        return null;
    }

    public Topic deleteTopic(long id) {
        for (int i = 0; i < topics.length; i++) {
            if (topics[i] != null && topics[i].getId() == id) {
                Topic deletedTopic = topics[i];
                topics[i] = null;
                return deletedTopic;
            }
        }

        return null;
    }

    public Topic[] getTopics() {
        return this.topics;
    }

    public UserArray getMembers() {
        return this.members;
    }
}
