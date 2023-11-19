package pgdp.messenger;

public class User {
    private long id;
    private String name;

    private User supervisor;
    private UserArray employees;

    public User(long id, String name, User supervisor) {
        this.id = id;
        this.name = name;
        this.supervisor = supervisor;
        employees = new UserArray(10);
    }

    /** Fügt den übergebenen Nutzer diesem Nutzer als Angestellten hinzu.
     * @param newEmployee Ein beliebiges Nutzer-Objekt
     */
    public void addEmployee(User newEmployee) {
        employees.addUser(newEmployee);
    }


    /** Entfernt den Nutzer mit der übergebenen ID aus dem Angestellten-Array dieses Nutzers.
     *  Gibt den entsprechend entfernten Nutzer zurück.
     *  Wenn kein Nutzer mit der ID existiert, wird 'null' zurückgegeben.
     * @param id Ein beliebiger long
     * @return Der entfernte Nutzer oder 'null'
     */
    public User deleteEmployee(long id) {
        return employees.deleteUser(id);
    }


    /** Gibt eine String-Repräsentation dieses Nutzers in der Form "ID:Name" zurück.
     * @return Die String-Repräsentation dieses Nutzers
     */
    public String toString() {
        return id + ":" + name;
    }

    /* ================ Getter und Setter ================ */

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public User getSupervisor() {
        return supervisor;
    }
    public UserArray getEmployees() {
        return employees;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }
    public void setEmployees(UserArray employees) {
        this.employees = employees;
    }
}