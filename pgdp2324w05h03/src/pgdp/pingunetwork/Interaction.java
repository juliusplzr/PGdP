package pgdp.pingunetwork;

// TODO: Fülle diese Klasse entsprechend der Aufgabenstellung
public class Interaction {
    private User user;
    private int interactionType;

    public Interaction(User user, int interactionType) {
        this.user = user;
        this.interactionType = interactionType;
    }

    public User getUser() {
        return user;
    }

    public int getInteractionType() {
        return interactionType;
    }
}
