package pgdp.messenger;

import java.time.*;

public class List {
	private ListElement head;
	private ListElement tail;
	private int size;

	public boolean isEmpty() {
		return head == null;
	}

	/** Fügt die übergebene 'message' am übergebenen 'index' ein.
	 *  Wenn der 'index' out of bounds liegt oder die 'message' 'null' ist, geschieht nichts.
	 * @param index Ein beliebiger Integer
	 * @param message Eine beliebige Message-Referenz
	 */
	public void insertAt(int index, Message message) {
		if (index > size || index < 0 || message == null) {
			return;
		}
		if (head == null) {
			head = tail = new ListElement(message, null);
		} else if (index == 0) {
			head = new ListElement(message, head);
		} else if (index == size) {
			add(message);
			return;
		} else {
			ListElement prev = null;
			ListElement current = head;
			for (int i = 0; i < index; i++) {
				prev = current;
				current = current.getNext();
			}
			prev.setNext(new ListElement(message, current));
		}
		size++;
	}

	/** Fügt die übergebene 'message' am Ende dieser Liste hinzu. Wenn die Message 'null' ist, geschieht nichts.
	 * @param message Eine beliebige Message-Referenz
	 */
	public void add(Message message) {
		if (message == null) {
			return;
		}
		if (tail == null) {
			head = tail = new ListElement(message, null);
		} else {
			tail.setNext(new ListElement(message, null));
			tail = tail.getNext();
		}
		size++;
	}

	/** Entfernt die übergebene 'message' (das konkrete Objekt) aus der Liste.
	 *  Wenn es nicht enthalten ist (oder 'message == null' ist), geschieht nichts.
	 * @param message Eine beliebige Message-Referenz
	 */
	public void delete(Message message) {
		ListElement prev = null;
		ListElement current = head;
		while (current != null) {
			if (current.getMessage() == message) {
				if (prev == null) {
					head = head.getNext();
				} else {
					prev.setNext(current.getNext());
					if (current.getNext() == null) {
						tail = prev;
					}
				}
				size--;
				return;
			}
			prev = current;
			current = current.getNext();
		}
	}

	/** Gibt die aktuelle Größe dieser Liste zurück.
	 * @return Die Größe dieser Liste
	 */
	public int size() {
		return size;
	}

	/** Gibt die Message an der 'index'-ten Stelle dieser Liste zurück.
	 *  Wenn der übergebene 'index' out of bounds liegt, wird 'null' zurückgegeben.
	 * @param index Ein beliebiger Integer
	 * @return Die gefundene Message oder 'null'
	 */
	public Message getByIndex(int index) {
		if (index >= size || index < 0) {
			return null;
		}
		ListElement current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current.getMessage();
	}

	/** Gibt die Message mit der übergebenen ID zurück, falls sie sich in der Liste befindet, 'null' sonst.
	 * @param id Ein beliebiger Long
	 * @return Die gefundene Message oder 'null'
	 */
	public Message getByID(long id) {
		for (int i = 0; i < size; i++) {
			Message message = this.getByIndex(i);

			if (message.getId() == id) {
				return message;
			}
		}

		return null;
	}

	/** Merged die übergebenen Listen in eine große Liste. Diese wird dann zurückgegeben.
	 * @param input Ein beliebiges Array von beliebigen Listen
	 * @return Die gemergte Liste
	 */
	public static List megaMerge(List... input) {
		int mergedSize = 0;

		if (input.length == 0) {
			return new List();
		}

		for (List list : input) {
			mergedSize += list.size;
		}

		List merged = new List();

		for (int i = 0; i < mergedSize; i++) {
			Message message = null;

			int index = -1;

			for (int j = 0; j < input.length; j++) {
				LocalDateTime lastTimestamp = message != null ? message.getTimestamp() : null;

				if (!input[j].isEmpty() && (message == null || input[j].head.getMessage().getTimestamp().isBefore(lastTimestamp))) {
					message = input[j].head.getMessage();
					index = j;
				}

			}

			if (index >= 0) {
				input[index].delete(message);
				merged.add(message);
			}

		}

		return merged;
	}

	/** Gibt eine neue Liste zurück, die alle Messages dieser Liste, deren Time-Stamp zwischen 'start' (inklusive)
	 *  und 'end' (exklusive) liegt, in der gleichen Reihenfolge enthält.
	 * @param start Ein beliebiges LocalDateTime-Objekt
	 * @param end Ein beliebiges LocalDateTime-Objekt
	 * @return Die gefilterte Liste
	 */
	public List filterDays(LocalDateTime start, LocalDateTime end) {
		if (start == null || end == null) {
			return new List();
		}

		if (end.isBefore(start)) {
			return new List();
		}

		List filtered = new List();

		for (int i = 0; i < this.size; i++) {
			Message message = this.getByIndex(i);
			LocalDateTime date = message.getTimestamp();

			if (date.isEqual(start) || date.isAfter(start) && date.isBefore(end)) {
				filtered.add(message);
			}
		}

		return filtered;
	}

	/** Gibt eine neue Liste zurück, die alle Messages dieser Liste, deren Nutzer gleich 'user' ist, enthält.
	 * @param user Eine beliebige User-Referenz
	 * @return Die gefilterte Liste
	 */
	public List filterUser(User user) {
		if (user == null) {
			return new List();
		}

		List filtered = new List();

		for (int i = 0; i < this.size; i++) {
			Message message = this.getByIndex(i);

			if (message.getAuthor() == user) {
				filtered.add(message);
			}
		}

		return filtered;
	}

	/** Gibt eine String-Repräsentation dieser Liste zurück. Dabei werden die String-Repräsentationen der einzelnen
	 *  Messages mit Zeilenumbrüchen aneinandergehängt.
	 * @return Die String-Repräsentation dieser Liste.
	 */
	public String toString() {
		if (this.isEmpty()) {
			return "";
		}

		String listString = "";
		for (int i = 0; i < size; i++) {
			Message message = this.getByIndex(i);
			listString += message.toString() + "\n";
		}

		return listString;
	}
}
