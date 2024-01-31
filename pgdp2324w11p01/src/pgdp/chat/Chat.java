package pgdp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static pgdp.PinguLib.*;

public class Chat {

    private Socket sock;
    private boolean isServer;
    private boolean initializationSuccessful;

    public static void main(String[] args) {
        new Chat().run();
    }

    public void run() {
        initializationSuccessful = false;
        initializeConnection();
        if (!initializationSuccessful) {
            return;
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             PrintWriter out = new PrintWriter(sock.getOutputStream(), true)) {
            if (isServer) {
                write("Verbindung hergestellt! Du kannst nun etwas senden.");
            } else {
                write("Verbindung hergestellt! Erwarte Nachricht vom Server.");
            }
            chatInTurns(out, in);
            write("Beenden.");

        } catch (IOException e) {
            write("Verbindungsfehler, Beenden.");
        } finally {
            terminateConnection();
        }
    }

    private void initializeConnection() {
        while (true) {
            write("Gib <port> an, um den Chatserver zu starten oder "
                    + "<host>:<port>, um dich mit einem laufenden Server zu verbinden.");
            write("Gib zum Beenden exit ein");
            String input = readString("");
            if (input.equals("exit")) {
                System.out.println("Beenden.");
                exit();
            }

            try {
                if (isServerInput(input)) {
                    int port = Integer.parseInt(input);
                    startServer(port);
                } else {
                    String host = getHost(input);
                    int port = getPort(input);
                    connectToServer(host, port);
                }
                break;
            } catch (NumberFormatException e) {
                write("Port ungültig/keine Zahl, versuche es erneut!");
            } catch (UnknownHostException e) {
                write("Host unbekannt, versuche es erneut!");
            } catch (BindException e) {
                write("Port Binding fehlgeschlagen, Prozess läuft bereits?");
            } catch (ConnectException e) {
                write("Verbindung abgelehnt, versuche es erneut!");
            } catch (IOException e) {
                write("Ein-/Ausgabefehler, versuche es erneut!");
            }
        }
        initializationSuccessful = true;
    }

    private void startServer(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        write("Warte auf Verbindung...");
        sock = server.accept();
        server.close();
        isServer = true;
    }

    private void connectToServer(String host, int port) throws IOException {
        sock = new Socket(host, port);
        isServer = false;
    }

    private void chatInTurns(PrintWriter out, BufferedReader in) throws IOException {
        boolean ourTurn = isServer;
        while (true) {
            if (ourTurn) {
                String input = readString("> ");
                if ("exit".equals(input)) {
                    out.println("exit");
                    break;
                }
                out.println(input);
            } else {
                String received = in.readLine();
                if ("exit".equals(received)) {
                    write("exit empfangen.");
                    break;
                }
                write(received);
            }
            ourTurn = !ourTurn;
        }
    }

    private void terminateConnection() {
        try {
            sock.close();
        } catch (IOException e) {
            write("Verbindungsabbau fehlgeschlagen.");
        }
    }

    public void exit() {
        System.exit(0);
    }

    private static boolean isServerInput(String input) {
        return !input.contains(":");
    }

    private static String getHost(String input) {
        return input.substring(0, input.indexOf(':'));
    }

    private static int getPort(String input) throws NumberFormatException {
        return Integer.parseInt(input.substring(input.indexOf(':') + 1));
    }

}
