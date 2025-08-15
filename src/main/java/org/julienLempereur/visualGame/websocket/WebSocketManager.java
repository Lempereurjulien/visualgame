package org.julienLempereur.visualGame.websocket;

public class WebSocketManager {
    public static InventaireWebSocket wsServer;

    public static void init(int port) throws Exception{
        if(wsServer == null){
            wsServer = new InventaireWebSocket(port);
            wsServer.start();
            System.out.println("✅ WebSocket démarré sur le port " + port);
        }
    }

    public static InventaireWebSocket getInstance() {
        if (wsServer == null) {
            throw new IllegalStateException("WebSocket non initialisé !");
        }
        return wsServer;
    }
}
