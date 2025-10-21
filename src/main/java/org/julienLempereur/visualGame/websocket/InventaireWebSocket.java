package org.julienLempereur.visualGame.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InventaireWebSocket extends WebSocketServer {

    private final Set<WebSocket> connections = ConcurrentHashMap.newKeySet();

    public InventaireWebSocket(int port){
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
    connections.add(webSocket);
    webSocket.send("connected");
    System.out.println("Client connected" + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        connections.remove(webSocket);
        System.out.println("Client disconnected" + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        System.out.println("Received message" + message);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
    e.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket server started successfully!");
    }

    public void broadCast(String message){
        for(WebSocket conn : connections){
            if(conn.isOpen()){
                conn.send(message);
            }
        }
    }

    public void broadCastMessage(String message){
        this.broadCastMessage(message);
    }
}
