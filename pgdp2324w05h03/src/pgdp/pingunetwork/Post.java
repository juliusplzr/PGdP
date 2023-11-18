package pgdp.pingunetwork;

public class Post {
    private String title;
    private String content;

    private Post[] comments;
    private Interaction[] interactions;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;

        comments = new Post[0];
        interactions = new Interaction[0];
    }

    /** Fügt die übergebene Interaktion in das 'interactions'-Array dieses Posts ein.
     * @param interaction Ein beliebiges Interaction-Objekt
     */
    public void addInteraction(Interaction interaction) {
        Interaction[] nInteractions = new Interaction[interactions.length + 1];

        for (int i = 0; i < interactions.length; i++) {
            if (interactions[i] == interaction) {
                return;
            }
            nInteractions[i] = interactions[i];
        }
        nInteractions[nInteractions.length - 1] = interaction;
        interactions = nInteractions;
    }

    /** Entfernt die übergebene Interaktion aus dem 'interactions'-Array dieses Posts, falls vorhanden.
     * @param interaction Ein beliebiges Interaction-Objekt.
     */
    public void removeInteraction(Interaction interaction) {
        Interaction[] nInteractions = new Interaction[interactions.length - 1];
        boolean found = false;

        for (int  i = 0; i < interactions.length; i++) {
            if (interactions[i] != interaction) {
                int index;
                if(found) {
                    index = i - 1;
                } else {
                    index = i;
                }
                nInteractions[index] = interactions[i];
            } else {
                found = true;
            }
        }
        if (found) {
            interactions = nInteractions;
        }
    }

    /** Fügt den übergebenen Kommentar in das 'comments'-Array dieses Posts ein.
     * @param comment Ein beliebiges Post-Objekt
     */
    public void addComment(Post comment) {
        Post[] nComments = new Post[comments.length + 1];

        for (int i = 0; i < comments.length; i++) {
            if (comments[i] == comment) {
                return;
            }
            nComments[i] = comments[i];
        }
        nComments[nComments.length - 1] = comment;
        comments = nComments;
    }

    /** Entfernt den übergebenen Kommentar aus dem 'comments'-Array dieses Posts, falls vorhanden.
     * @param comment Ein beliebiges Post-Objekt.
     */
    public void removeComment(Post comment) {
        Post[] nPosts = new Post[comments.length - 1];
        boolean found = false;

        for (int  i = 0; i < comments.length; i++) {
            if (comments[i] != comment) {
                int index;
                if(found) {
                    index = i - 1;
                } else {
                    index = i;
                }
                nPosts[index] = comments[i];
            } else {
                found = true;
            }
        }
        if (found) {
            comments = nPosts;
        }
    }

    /* ================ Getter und Setter ================ */

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public Post[] getComments() {
        return comments;
    }
    public Interaction[] getInteractions() {
        return interactions;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
