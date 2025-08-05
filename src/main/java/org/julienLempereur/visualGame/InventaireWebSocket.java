package org.julienLempereur.visualGame;

import org.eclipse.jetty.util.ajax.JSON;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
public class InventaireWebSocket extends WebSocketServer {

    private final Set<WebSocket> clients = new HashSet<>();

    public InventaireWebSocket(int port){
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        clients.add(webSocket);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    public void broadcastMessage(String message){

        if (getConnections().isEmpty()) {
            System.out.println("❌ Aucun client WebSocket connecté.");
            return;
        }

        for (WebSocket conn : getConnections()) {
            if (conn.isOpen()) {
                conn.send(message);
            }
        }
    }

    @Override
    public void onStart() {
    System.out.println("Websocket démarré !");
    }
}

