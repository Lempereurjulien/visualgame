package org.julienLempereur.visualGame.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.julienLempereur.visualGame.model.PlayerModel;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InventaireWebSocket extends WebSocketServer {

    private final Set<WebSocket> connections = ConcurrentHashMap.newKeySet();

    public InventaireWebSocket(int port){
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
    connections.add(webSocket);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "connected");
//    webSocket.send(new Gson().toJson(response));
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

    public void broadCastAllPlayers(List<PlayerModel> players) {
        //Envoie tous les joueurs en les transformant en Json
        System.out.println("Connexions actives : " + players);
        String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(players);
        Map<String, String> response = new HashMap<>();
        response.put("players", json);
        broadCast(response);
    }

    public void broadCastMessage(String message){
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        this.broadCast(response);

    }

    /// Envoie le broadCast générale
    private void broadCast(Map<String, String> mapResponse){
        if (connections.isEmpty()) {
            System.out.println("⚠️ Aucun client connecté, message ignoré");
            return;
        }
        for(WebSocket conn : connections){
            if(conn.isOpen()){
                conn.send(new Gson().toJson(mapResponse));
            }
        }
    }

}
