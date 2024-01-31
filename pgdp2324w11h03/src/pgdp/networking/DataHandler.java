package pgdp.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import pgdp.networking.DataHandler.ConnectionException;
import pgdp.networking.ViewController.Message;
import pgdp.networking.ViewController.User;

public class DataHandler {

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    
    private Queue<Byte> handshakeMutex;
    private Thread inputHandler;
    
    private HttpClient client;
    private int id;
    private String username;
    private String password;
    
    public static String serverAddress = "pgdp.pl.cit.tum.de";
    
    private final static byte SUPPORTED_VERSION = 42;
    
    boolean connected;
    
    /**
     * Erstellt neuen HTTP Client für die Verbindung zum Server
     */
    public DataHandler() {
        handshakeMutex = new LinkedList<>();
        
        /************************
         * Your Code goes here: *
         ************************/
        client = HttpClient.newBuilder()
                //.proxy(ProxySelector.of(new InetSocketAddress("localhost", 8080)))
                .version(Version.HTTP_1_1)
                .followRedirects(Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }
    
/************************************************************************************************************************    
*                                                                                                                       *
*                                       HTTP Handling                                                                   *
*                                                                                                                       *
*************************************************************************************************************************/
    
    /**
     * Registriert den Nutzer beim Server oder erfragt ein neues Passwort
     * Gibt bei Erfolg true zurück.
     * Endpoint: /api/user/register
     * @param username Nutzername
     * @param kennung TUM Kennung
     * @return Registrierung erfolgreich
     */
    public boolean register(String username, String kennung) {
        
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://" + serverAddress + "/api/user/register"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString("{\"username\": \"" + username + "\",\"tum_kennung\": \"" + kennung + "\"}"))
                .build();
        
        HttpResponse<String> response = null;
        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        
        System.out.print(response.body());
        
        return response.statusCode() == 200;
    }
    
    /**
     * Hilfsmethode um nach erfolgreichem login einen Autentifizierungstoken zu erhalten.
     * Returns null upon failure
     * @return Authentication token or null
     */
    public String requestToken() {
        
        if (this.username == null || this.password == null) {
            return null;
        }
        
        System.out.println(username + ";" + password);
        return requestToken(this.username, this.password);
    }
    
    /**
     * Erfragt Autentifizierungstoken vom Server.
     * Gibt null bei Fehler zurück
     * Endpoint: /token
     * @param username Nutzername
     * @param password Passwort
     * @return token oder null
     */
    private String requestToken(String username, String password) {
        
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://" + serverAddress + "/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(BodyPublishers.ofString("username=" + username + "&password=" + password))
                .build();
        
        HttpResponse<String> response = null;
        System.out.println(request);
        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        System.out.print(request);
        if (response.statusCode() != 200) {
            return null;
        }
        
        JSONObject json = new JSONObject(response.body());
        
        return json.getString("access_token");
    }
    
    /**
     * Initialer login. 
     * Wenn ein Token mit Nutzername und Passwort erhalten wird, werden diese gespeichert.
     * Anschließend wird die Nutzer ID geladen.
     * Endpoint: /token
     *           /api/user/me
     * @param username Nutzername
     * @param password Passwort
     * @return Login erfolgreich
     */
    public boolean login(String username, String password) {
        System.out.println("Test");
        String token = requestToken(username, password);
        if (token == null) {
            return false;
        }

        this.username = username;
        this.password = password;
        
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://" + serverAddress + "/api/user/me/"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        
        HttpResponse<String> response = null;
        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return false;
        }
            
        if (response.statusCode() != 200) {
            return false;
        }
        
        JSONObject json = new JSONObject(response.body());
        this.id = json.getInt("id");
        
        return true;
    }
    
    /**
     * Erfragt alle öffentlichen Nutzer vom Server
     * Endpoint: /api/users
     * @return Map von Nutzern und IDs
     */
    public Map<Integer, User> getContacts() {
        
        Map<Integer, User> users = new HashMap<>();
        
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://" + serverAddress + "/api/users"))
                .header("Authorization", "Bearer " + requestToken())
                .GET()
                .build();
        
        HttpResponse<String> response = null;
        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return null;
        }
        
        if (response.statusCode() != 200) {
            return null;
        }
        
        JSONArray json = new JSONArray(response.body());
        
        for (Object obj : json) {
            
            JSONObject jobj = (JSONObject) obj;
            
            users.put(jobj.getInt("id"), new User(jobj.getInt("id"), jobj.getString("username"), new ArrayList<>()));
        }
        
        return users;
    }
    
    /**
     * Erfragt alle Nachrichten welche mit einem gewissen Nutzer ausgetauscht wurden.
     * Endpoint: /api/messages/with/
     * @param id ID des Partners
     * @param count Anzahl der zu ladenden Nachrichten
     * @param page Falls count gesetzt, gibt die Seite an Nachrichten an.
     * @return Liste der Abgefragten NAchrichten.
     */
    public List<Message> getMessagesWithUser(int id, int count, int page) {
        
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://" + serverAddress + "/api/messages/with/" 
                        + Long.toString(id) 
                        + "?limit=" + Integer.toString(count)
                        + "&pagination=" + Integer.toString(page)))
                .header("Authorization", "Bearer " + requestToken())
                .GET()
                .build();
        
        HttpResponse<String> response = null;
            try {
                response = client.send(request, BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                return null;
                
            }
        
        if (response.statusCode() != 200) {
            return null;
        }
        
        JSONArray json = new JSONArray(response.body());
        
        List<Message> messages = new ArrayList<>();
        
        for (Object obj : json) {
            
            JSONObject jobj = (JSONObject) obj;
            
            messages.add(new Message(LocalDateTime.parse(jobj.getString("time")), jobj.getString("text"), id == jobj.getInt("to_id"), jobj.getInt("id")));
        }
        
        messages.sort((m1, m2) -> m1.date().compareTo(m2.date()));
        return messages;
    }
    
/************************************************************************************************************************    
*                                                                                                                       *
*                                       Socket Handling                                                                 *
*                                                                                                                       *
*************************************************************************************************************************/    
    
    /**
     * Thread Methode um ankommende Nachrichten zu behandeln
     */
    private void handleInput() {
        
        System.out.println("Input Handler started");
        
        try {
            while (true) {
                
                byte type = in.readByte();
                System.out.println("Recieved Message");
                
                switch (type) {
                    case 0 -> {
                        byte hsType = in.readByte();
                        if (hsType == 5) {
                            
                            passHandshakeMessage(new byte[] {type, hsType});
                        }
                    }
                    case 1 -> {

                        int length = ((in.readByte()&0xff)<<8) | (in.readByte()&0xff);

                        byte[] content = new byte[length];
                        in.read(content);

                        displayMessage(new String(content, StandardCharsets.UTF_8));
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Erstelle einen Socket und Verbinde mit dem Server.
     * Gebe Nutzer ID und Token an. Verifiziert Server Antworten
     * @throws ConnectionException 
     */
    private void connect() throws ConnectionException {
        try {
            socket = new Socket(serverAddress, 80);
            System.out.println("Connecting");
            
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
            byte[] b = new byte[3];
            in.read(b);
            
            if (!Arrays.equals(b, new byte[] {0,0,SUPPORTED_VERSION})) {
                throw new ConnectionException("Invalid Server Hello");
            }
            
            byte[] hsID = {0,1,0,2,8,0,0,0,0,0,0,0,0};
            
            int tmpId = id;
            
            for (int i = 0; i < 8; i++) {
                hsID[hsID.length-i-1] = (byte) tmpId;
                tmpId >>>= 8;
            }
            
            out.write(hsID);
            
            String token = requestToken();
            
            byte[] hsToken = new byte[token.length()+4];
            hsToken[0] = 0;
            hsToken[1] = 3;
            hsToken[2] = (byte) (token.length()>>>8);
            hsToken[3] = (byte) token.length();
            
            for (int i = 0; i < token.length(); i++) {
                hsToken[i+4] = (byte) token.charAt(i);
            }
            
            out.write(hsToken);
            
            startInputHandler();
            
            connected = true;
        } catch (Throwable t) {
            
            if (t.getClass().equals(ConnectionException.class)) {
                throw (ConnectionException)t;
            }
            
            t.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Wechselt die Verbindung zu einem anderen Chatpartner
     * @param partnerID
     * @throws ConnectionException 
     */
    public void switchConnection(int partnerID) throws ConnectionException{
        
        try {
        
            if (!connected) {
                connect();
            }
            
            byte[] hsID = {0,4,8,0,0,0,0,0,0,0,0};
            
            for (int i = 0; i < 8; i++) {
                hsID[hsID.length-i-1] = (byte) partnerID;
                partnerID >>>= 8;
            }
            
            out.write(hsID);
            
            System.out.println("Sent switch request");
            
            byte[] resp = getResponse(2);
            
            if (!Arrays.equals(resp, new byte[] {0,5})) {
                throw new ConnectionException("Server has not Acknowledged change of Partner.");
            }
        } catch (Throwable t) {
            
            if (t.getClass().equals(ConnectionException.class)) {
                throw (ConnectionException)t;
            }
            t.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Sende eine Nachricht an den momentan ausgewählten Nutzer.
     * @param message
     */
    public void sendMessage(String message) {
        
        byte[] buf = Charset.forName("UTF-8").encode(message).array();
    
        int length = Math.min(buf.length, 0xffff);
        
        byte[] messagePacket = new byte[length+3];
        
        messagePacket[0] = 1;
        messagePacket[1] = (byte) (length >>> 8);
        messagePacket[2] = (byte) length;
        
        for (int i = 0; i < length; i++) {
            messagePacket[i+3] = buf[i];
        }
        
        try {
            out.write(messagePacket);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(-1);
        }
        
    }
    

    /**
     * Holt sich length bytes vom empfänger Thread
     * @param length anzahl an bytes
     * @return
     */
    private byte[] getResponse(int length) {
        
        boolean wait = true;
        byte[] resp = new byte[length];
        
        synchronized(handshakeMutex) {
            wait = handshakeMutex.size() < length;
        }
        
        while (wait) {
            synchronized(inputHandler) {
                try {
                    inputHandler.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            synchronized(handshakeMutex) {
                wait = handshakeMutex.size() < length;
            }
        }
        
        synchronized(handshakeMutex) {
            for (int i = 0; i < resp.length; i++) {
                resp[i] = handshakeMutex.remove();
            }
        }
        return resp;
    }
    
    /**
     * Startet einen neuen thread für das input handling.
     */
    private void startInputHandler() {
        
        inputHandler = new Thread() {
            @Override
            public void run() {
                handleInput();
            }
        };
        inputHandler.start();
        
    }

    /**
     * Übergibt eine Nachricht an die Nutzeroberfläche
     * @param content Nachrichten inhalt
     */
    private void displayMessage(String content) {
        Platform.runLater(() -> {
            ViewController.displayMessage(ViewController.currentChat, new Message(LocalDateTime.now(), content, false, 0));
        });
    }

    /**
     * Übergibt eine Handshake Nachricht an den Hauptthread
     * @param handshake Nachricht
     */
    private void passHandshakeMessage(byte[] handshake) {
        synchronized(handshakeMutex) {

            for (byte b : handshake) {
                handshakeMutex.add(b);
            }
        }
        
        synchronized(inputHandler) {
            inputHandler.notifyAll();
        }
        System.out.println("Notified main thread");
    }

    /**
     * Setter fürs testing
     * @param client
     */
    public void setClient(HttpClient client) {
        this.client = client;
    }
    
    /**
     * Schlißet offene Verbindungen
     */
    public void close() {
        if (inputHandler != null) {
            inputHandler.interrupt();
        }
            if (socket != null) {
            try {
                out.write(new byte[] {0,-1});
                socket.close();
            } catch (IOException e) {
                // pass
            }
        }
    }
    
    public static class ConnectionException extends Exception {

        private static final long serialVersionUID = 9055969838018372992L;
        
        public ConnectionException() {super();}
        public ConnectionException(String message) {super(message);}
        
    }
}
