package org.julienLempereur.visualGame.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.julienLempereur.visualGame.model.MapModel;
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

    /// Envoie le broadCast générale
    private void broadCast(String mapResponse){
        if (connections.isEmpty()) {
            System.out.println("⚠️ Aucun client connecté, message ignoré");
            return;
        }
        for(WebSocket conn : connections){
            if(conn.isOpen()){
                conn.send(mapResponse);
            }
        }
    }

//BroadCast Métier
    public void broadCastAllPlayers(List<PlayerModel> players) {
        //Envoie tous les joueurs en les transformant en Json
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        JsonObject response = new JsonObject();
        response.addProperty("type", "players");
        response.add("data", gson.toJsonTree(players));
        broadCast(response.toString());
    }

    public void broadCastMap(MapModel map){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        JsonObject response = new JsonObject();
        response.addProperty("type", "map");
        response.add("data", gson.toJsonTree(map));
        broadCast(response.toString());
    }

}
