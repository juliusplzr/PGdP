package pgdp.pingunetwork;

// TODO: Erweitere die Klasse entsprechend der Aufgabenstellung so, dass ein Bild für die Gruppe dargestellt werden kann.
public class Group {
    private User owner;
    private String name;
    private String description;
    private User[] members;
    private Picture picture;

    public Group(String name, String description, User owner, Picture picture) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.picture = picture;
        members = new User[1];
        members[0] = owner;
    }

    /** Fügt den übergebenen Nutzer in das 'members'-Array mit ein.
     * @param user Ein beliebiges User-Objekt
     */
    public void addUser(User user) {
        User[] nMembers = new User[members.length + 1];

        for (int  i = 0; i < members.length; i++) {
            if (members[i] == user) {
                return;
            }
            nMembers[i] = members[i];
        }
        nMembers[nMembers.length - 1] = user;
        members = nMembers;

    }

    /** Entfernt das übergebene User-Objekt aus dem 'members'-Array.
     *  Wenn der entfernte Nutzer der Gruppen-Admin war, wird diese Position
     *  entsprechend auf den ersten im übrigen Array angepasst.
     *  Wenn der übergebene Nutzer gar nicht in der Gruppe ist, geschieht nichts.
     * @param user Ein beliebiges User-Objekt.
     */
    public void removeUser(User user) {
        User[] members = new User[this.members.length - 1];

        int userIndex = -1;

        for (int i = 0; i < this.members.length; i++) {
            if (this.members[i] == user) {
                userIndex = i;
                break;
            }
        }

        if (userIndex == -1) {
            return;
        } else {
            for (int i = 0; i < userIndex; i++) {
                members[i] = this.members[i];
            }

            for (int i = userIndex + 1; i < this.members.length; i++) {
                members[i - 1] = this.members[i];
            }

            this.members = members;
        }

        if (user == owner) {
            if (members.length == 0) {
                this.owner = null;
            } else {
                this.owner = this.members[0];
            }
        }
    }

    /* ================ Getter und Setter ================ */

    public User getOwner() {
        return owner;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public User[] getMembers() {
        return members;
    }

    public void setOwner(User owner) {
        for (int i = 0; i < members.length; i++) {
            if (members[i] == owner) {
                this.owner = owner;
                break;
            }
        }
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
