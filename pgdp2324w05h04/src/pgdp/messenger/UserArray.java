package pgdp.messenger;

public class UserArray {
	// TODO: Implementiere die fehlenden Attribute
	private User[] users;
	private int currentSize;

	public UserArray(int initCapacity) {
		this.users = initCapacity > 1 ? new User[initCapacity] : new User[1];
	}

	/** Fügt den übergebenen Nutzer in das durch dieses Objekt dargestellte 'UserArray' ein
	 * @param user Eine beliebige User-Referenz (schließt 'null' mit ein)
	 */
	public void addUser(User user) {
		if (user == null) {
			return;
		}

        for (int i = 0; i < users.length; i++) {
			if (users[i] == null) {
				users[i] = user;
				currentSize++;
				return;
			}
		}

		User[] nUsers = new User[2 * users.length];
        System.arraycopy(users, 0, nUsers, 0, users.length);
		users = nUsers;
		users[currentSize++] = user;
	}

	/** Entfernt den Nutzer mit der übergebenen ID aus dem Array und gibt diesen zurück.
	 *  Wenn kein solcher Nutzer existiert, wird 'null' zurückgegeben.
	 * @param id Ein beliebiger long
	 * @return Der eben aus dem UserArray entfernte Nutzer, wenn ein Nutzer mit der übergebenen id darin existiert, sonst 'null'
	 */
	public User deleteUser(long id) {
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null && users[i].getId() == id) {
				User deletedUser = users[i];
				users[i] = null;
				currentSize--;
				return deletedUser;
			}
		}

		return null;
	}

	// TODO: Implementiere die fehlenden Methoden!

	public int size() {
		return currentSize;
	}

	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}
}
