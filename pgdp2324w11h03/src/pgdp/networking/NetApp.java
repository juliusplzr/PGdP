package pgdp.networking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pgdp.networking.DataHandler.ConnectionException;
import pgdp.networking.ViewController.Message;

public class NetApp extends Application {

	public static final Background LIGHT_GRAY_BACKGROUND = new Background(
			new BackgroundFill(Color.rgb(64, 68, 75), null, null));
	public static final Background GRAY_BACKGROUND = new Background(
			new BackgroundFill(Color.rgb(54, 57, 63), null, null));

	public static void startApp(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
        
        ViewController.contacts = new HashMap<>();

        EventHandler<WindowEvent> onClose = e -> ViewController.dh.close();
        
        stage.setTitle("PinguChat");
		stage.setMinHeight(400);
		stage.setMinWidth(800);
		stage.setScene(new Scene(createView()));
        stage.setOnCloseRequest(onClose);
		stage.show();
	}

	/**
	 * Creates overall view.
	 * 
	 * @return the final HBox to be put into the scene
	 */
	public StackPane createView() {
		StackPane sp = new StackPane();
		HBox chatView = new HBox();
		sp.getChildren().addAll(chatView, createLogin());

		chatView.getChildren().addAll(createChatSelect(), createChatView());
		chatView.backgroundProperty().set(GRAY_BACKGROUND);

		return sp;
	}

	/**
	 * Creates login view. Pressing enter in any TextField will have the same effect
	 * as hitting the login button. Request Password will display the password in
	 * the second TextField, if successful. If any problem occurs one or both
	 * TextFields will get a red border.
	 * 
	 * @return login view
	 */
	public VBox createLogin() {
		VBox vb = new VBox();
        TextField username = new TextField();
        TextField kennung = new TextField();
		TextField password = new TextField();
		Label request = new Label("Request Password");
		Label login = new Label("Login");
		vb.getChildren().addAll(username, kennung, password, request, login);

		vb.setMaxWidth(Double.MAX_VALUE);
		vb.setMaxHeight(Double.MAX_VALUE);
		vb.setBackground(GRAY_BACKGROUND);
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(10);

        username.setMaxWidth(300);
        username.setStyle("-fx-control-inner-background: rgb(64, 68, 75);\n"
                + " -fx-background-color: -fx-control-inner-background;\n"
                + " -fx-prompt-text-fill: rgb(142, 146, 153)");
        username.setPromptText("username");
        username.setBorder(new Border(
                new BorderStroke(Color.rgb(142, 146, 153), BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if (ViewController.login(username.getText(), password.getText())) {
                    addContacts(ViewController.chatSelectVBox);
                    vb.toBack();
                } else {
                    username.setBorder(
                            new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
                    password.setBorder(
                            new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
                }
            }
        });

        kennung.setMaxWidth(300);
        kennung.setStyle("-fx-control-inner-background: rgb(64, 68, 75);\n"
                + " -fx-background-color: -fx-control-inner-background;\n"
                + " -fx-prompt-text-fill: rgb(142, 146, 153)");
        kennung.setPromptText("TUM Kennung");
        kennung.setBorder(new Border(
                new BorderStroke(Color.rgb(142, 146, 153), BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
        kennung.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if (ViewController.login(username.getText(), password.getText())) {
                    addContacts(ViewController.chatSelectVBox);
                    vb.toBack();
                } else {
                    kennung.setBorder(
                            new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
                    kennung.setBorder(
                            new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
                }
            }
        });

		password.setMaxWidth(300);
		password.setStyle("-fx-control-inner-background: rgb(64, 68, 75);\n"
				+ " -fx-background-color: -fx-control-inner-background;\n"
				+ " -fx-prompt-text-fill: rgb(142, 146, 153)");
		password.setPromptText("password");
		password.setBorder(new Border(
				new BorderStroke(Color.rgb(142, 146, 153), BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
		password.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				event.consume();
				if (ViewController.login(username.getText(), password.getText())) {
                    addContacts(ViewController.chatSelectVBox);
					vb.toBack();
				} else {
					username.setBorder(
							new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
					password.setBorder(
							new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
				}
			}
		});

		request.setTextFill(Color.WHITE);
		request.setBorder(new Border(
				new BorderStroke(Color.rgb(142, 146, 153), BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
		request.setPadding(new Insets(5));
		request.setMaxWidth(150);
		request.setAlignment(Pos.CENTER);
		request.setOnMouseClicked(event -> {
			if (!ViewController.requestPassword(username.getText(), kennung.getText())) {
                username.setBorder(
                        new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
                kennung.setBorder(
                        new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
			}
            
		});

		login.setTextFill(Color.WHITE);
		login.setBorder(new Border(
				new BorderStroke(Color.rgb(142, 146, 153), BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
		login.setPadding(new Insets(5));
		login.setMaxWidth(150);
		login.setAlignment(Pos.CENTER);
		login.setOnMouseClicked(event -> {
			if (ViewController.login(username.getText(), password.getText())) {
                addContacts(ViewController.chatSelectVBox);
				vb.toBack();
			} else {
				username.setBorder(
						new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
				password.setBorder(
						new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));
			}
		});

		return vb;
	}

	/**
	 * Creates the scroll pane where the user can select a contact to start
	 * chatting.
	 * 
	 * @return select window
	 */
	public ScrollPane createChatSelect() {
		ScrollPane sp = new ScrollPane();
		VBox chats = ViewController.chatSelectVBox;

		// Properties
		sp.setMinWidth(305);
		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setStyle("-fx-background: rgb(47, 49, 54);\n -fx-background-color: rgb(47, 49, 54)");
		sp.setContent(chats);

		chats.setPrefSize(300, 300);
		chats.setFillWidth(true);

		return sp;
	}
	
	public void addContacts(VBox chats) {

        // Add all available contacts
        ViewController.contacts.entrySet().stream().sorted((c1, c2) -> {
            List<Message> l1 = c1.getValue().messages();
            List<Message> l2 = c2.getValue().messages();
            if (l1.isEmpty() && l2.isEmpty()) {
                return c1.getValue().name().compareTo(c2.getValue().name());
            }
            return l1.isEmpty() ? 1
                    : l2.isEmpty() ? -1 : l2.get(l2.size() - 1).date().compareTo(l1.get(l1.size() - 1).date());
        }).forEachOrdered(e -> chats.getChildren().add(createChatButton(e.getValue().name(), e.getKey())));
    }

	/**
	 * Creates for the given name and id a new label. Calls method when clicking
	 * onto it.
	 * 
	 * @param name name of the chat partner
	 * @param id   id of the chat partner
	 * @return label
	 */
	public Label createChatButton(String name, int id) {
		Label l = new Label(name);
		l.setMaxWidth(Double.MAX_VALUE);
		l.setAlignment(Pos.CENTER_LEFT);
		l.setPadding(new Insets(5, 10, 5, 10));
		l.setFont(Font.font(16));
		l.setId(id + "");
		l.setOnMouseClicked(event -> ViewController.selectUser(id));
		return l;
	}

	/**
	 * Creates the right pane of the view, where the user can write and read
	 * messages.
	 * 
	 * @return chat window
	 */
	public BorderPane createChatView() {
		BorderPane bp = new BorderPane();
		Label name = ViewController.contactLabel;

		bp.setTop(name);
		bp.setBottom(createTextInput());
		bp.setCenter(createMessageView());

		bp.backgroundProperty().set(GRAY_BACKGROUND);
		HBox.setHgrow(bp, Priority.ALWAYS);

		name.setText("Select a chat");
		name.setMaxWidth(Double.MAX_VALUE);
		name.setTextFill(Color.WHITE);
		name.setFont(Font.font(16));
		name.setPadding(new Insets(5, 10, 5, 10));
		name.setBackground(LIGHT_GRAY_BACKGROUND);

		return bp;
	}

	/**
	 * Creates the scroll pane of the message history
	 * 
	 * @return message history
	 */
	public ScrollPane createMessageView() {
		ScrollPane sp = new ScrollPane();
		VBox vb = ViewController.messageVBox;
		sp.setContent(vb);

		sp.setPrefHeight(200);
		sp.setFitToWidth(true);
		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setStyle("-fx-background: rgb(54, 57, 63);\n -fx-background-color: rgb(54, 57, 63)");
		sp.vvalueProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					if (newValue.doubleValue() == 0) {
						ViewController.addNext50MessagesToView();
					}
				});

		vb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		vb.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue,
				Number newValue) -> sp.setVvalue((Double) newValue));
		return sp;
	}

	/**
	 * Creates the text area where the user can input messages. Prevents user from
	 * sending empty messages. Sends messages on pressing Enter. Adds new line on
	 * pressing Shift+Enter.
	 * 
	 * @return
	 */
	public TextArea createTextInput() {
		TextArea input = ViewController.inputArea;
		input.setWrapText(true);
		input.setPrefHeight(100);
		input.setStyle("-fx-control-inner-background: rgb(64, 68, 75);\n"
				+ " -fx-background-color: -fx-control-inner-background;\n"
				+ " -fx-prompt-text-fill: rgb(142, 146, 153)");
		input.setPromptText("Select a chat first");

		input.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				event.consume();
				if (event.isShiftDown()) {
					input.appendText(System.lineSeparator());
				} else if (!input.getText().chars().filter(e -> e != ' ' && e != '\n' && e != '\t')
						.mapToObj(e -> (char) e).map(e -> e.toString()).collect(Collectors.joining()).equals("")) {
					ViewController.displayMessage(ViewController.currentChat,
							new Message(LocalDateTime.now(), input.getText().strip(), true, -1));
					
					ViewController.dh.sendMessage(input.getText().strip());
					input.clear();
				}
			}
		});

		return input;
	}
}
