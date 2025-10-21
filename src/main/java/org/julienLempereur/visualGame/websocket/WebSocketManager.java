package org.julienLempereur.visualGame.websocket;

public class WebSocketManager {

    public static InventaireWebSocket wsServer;

    public static void start(int port){
        try{
            if (wsServer == null){
                wsServer = new InventaireWebSocket(port);
                wsServer.setReuseAddr(true);
                wsServer.start();
                System.out.println("Websocket start on port : " + port);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void stop(){
        try{
            if(wsServer != null){
                wsServer.stop();
                wsServer = null;
                System.out.println("WebSocket stopped");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restart(int port){
        stop();
        start(port);
    }

    public static InventaireWebSocket getInstance(){
        if(wsServer == null){
            throw new IllegalStateException("Websocket non initialisé");
        }
        return wsServer;
    }

//    public static String objectToJson(<T> object){
//
//    }
}
