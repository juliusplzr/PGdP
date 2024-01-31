package pgdp.networking;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.stream.*;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import pgdp.networking.DataHandler.ConnectionException;

public class ViewController {

	public record User(int id, String name, List<Message> messages) {
	}

	public record Message(LocalDateTime date, String content, boolean self, int id) {
	}

	public static VBox chatSelectVBox = new VBox();
	public static VBox messageVBox = new VBox();
	public static Label contactLabel = new Label();
	public static TextArea inputArea = new TextArea();

	public static Map<Integer, User> contacts = new HashMap<>();
	public static int currentChat;
	
	public static DataHandler dh = new DataHandler();

	/**
	 * Connects this client to the server by username and password. username and
	 * password have to be not-blank.
	 * 
	 * @param username User name of the client
	 * @param password Password of the client
	 * @return true if connection was successful
	 */
	public static boolean login(String username, String password) {
		if (username.isBlank() || password.isBlank()) {
			return false;
		}
		
		boolean login = dh.login(username, password);
		
		if (login) {
		    contacts = dh.getContacts();
		}
		
		return login;
	}

	/**
	 * Requests a new password for the provided username.
	 * 
	 * @param username User name of the client
	 * @return newly created password or null, if username was invalid or something
	 *         else went wrong
	 */
	public static boolean requestPassword(String username, String kennung) {
	
	    return dh.register(username, kennung);
	}

	/**
	 * Loads the next (max) 50 earlier messages prior to the current earliest
	 * message if existent. Gets invoked by scrolling to the top of the chat
	 * history.
	 */
	public static void addNext50MessagesToView() {
		
	    User current = contacts.get(currentChat);
	    int messages = current.messages.size()/50;
	    dh.getMessagesWithUser(current.id, 50, messages).stream().forEach(m -> displayMessage(currentChat, m));
	}

	/**
	 * Adds a new message to the selected chat. If id and currentChat match, the
	 * message will be displayed immediately. The corresponding label to this chat
	 * will be moved to top in chatSelectBox.
	 * 
	 * @param id ID of the chat partner (not the own ID, even if the sender is the
	 *           chat partner)
	 * @param m  new message to be added
	 */
	public static void displayMessage(int id, Message m) {
		if (id == currentChat) {
			// should only be added to view, if the desired chat is selected
			messageVBox.getChildren().add(createMessageBox(m));
		}

		if (contacts.containsKey(id)) {
			contacts.get(currentChat).messages().add(m);
		} else {
			contacts.put(id, new User(id, "", Stream.of(m).collect(Collectors.toList())));
		}

		// move chat to top
		int index = -1;
		for (int i = 0; i < chatSelectVBox.getChildren().size(); i++) {
			if (chatSelectVBox.getChildren().get(i).getId().equals(id + "")) {
				index = i;
			}
		}
		if (index != -1) {
			chatSelectVBox.getChildren().add(0, chatSelectVBox.getChildren().remove(index));
		}
	}

	/**
	 * Selects the chat to be displayed now by id. Gets invoked by clicking on the
	 * corresponding label in the left scroll pane.
	 * 
	 * @param id id of the chat
	 */
	public static void selectUser(int id) {
		currentChat = id;
		messageVBox.getChildren().clear();
		User user = contacts.get(id);
		user.messages.clear();
		user.messages.addAll(dh.getMessagesWithUser(id, 50, 0));
		user.messages().stream().forEachOrdered(e -> messageVBox.getChildren().add(createMessageBox(e)));
		contactLabel.setText(contacts.get(id).name());
		inputArea.setPromptText("Message @" + contactLabel.getText());
		inputArea.requestFocus();
		
		try {
            dh.switchConnection(id);
        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }
	}

	/**
	 * Creates a message box to be displayed within the app. Alignment to left or
	 * right side is determined by the author (self or other)
	 * 
	 * @param m message to be displayed
	 * @return message box
	 */
	public static HBox createMessageBox(Message m) {
		HBox hb = new HBox();
		StackPane space = new StackPane();
		VBox message = new VBox();
		Label text = new Label(m.content());
		LocalDateTime ldt = m.date();
		Label date = new Label(ldt.getDayOfMonth() + "." + ldt.getMonthValue() + "." + ldt.getYear() + " "
				+ ldt.getHour() + ":" + ldt.getMinute());

		message.getChildren().addAll(text, date);

		if (m.self()) {
			hb.getChildren().addAll(space, message);
			text.setAlignment(Pos.CENTER_RIGHT);
			date.setAlignment(Pos.CENTER_RIGHT);
		} else {
			hb.getChildren().addAll(message, space);
			text.setAlignment(Pos.CENTER_LEFT);
			date.setAlignment(Pos.CENTER_LEFT);
		}

		hb.setMaxWidth(Double.MAX_VALUE);

		space.setMaxWidth(200);
		space.setMinWidth(200);

		message.setMaxWidth(Double.MAX_VALUE);

		text.setMaxWidth(Double.MAX_VALUE);
		text.setWrapText(true);
		text.setPadding(new Insets(5, 20, 0, 20));
		HBox.setHgrow(message, Priority.ALWAYS);

		date.setMaxWidth(Double.MAX_VALUE);
		date.setFont(Font.font(8));
		date.setTextFill(Color.rgb(142, 146, 153));
		date.setPadding(new Insets(0, 20, 5, 20));

		return hb;
	}

}
