package org.julienLempereur.visualGame;
import static spark.Spark.*;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.julienLempereur.visualGame.services.MapService;
import org.julienLempereur.visualGame.services.MapServiceImpl;
import org.julienLempereur.visualGame.services.PlayerService;
import org.julienLempereur.visualGame.services.PlayerServiceImpl;
//import org.julienLempereur.visualGame.services.PlayerServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SparkService {

    MapService mapService = new MapServiceImpl();
    PlayerService playerService = new PlayerServiceImpl();
    private final Gson gson = new Gson();
    public SparkService(){
    port(4567);
    enableCORS("*","*","*");

    get("/hello", (request, response) -> "Hello world");

    /// Verification du code
    post("/code", ((request, res) -> {
        res.type("application/json");
        Map<String, String> body = gson.fromJson(request.body(), Map.class);
        String uuidSend = body.get("uuid");
        Map<String, Object> response = new HashMap<>();
        if(Objects.equals(uuidSend, CommonClass.getInstance().getUuid())){
            response.put("response", true);
        }
        else{
            response.put("response",false);
        }
        return gson.toJson(response);
    }));

    post("/starterpack", (req, res) ->{
        res.type("application/json");
        Map<String, String> body = gson.fromJson(req.body(), Map.class);
        String playerName = body.get("namePlayer");
        if (playerName == null || playerName.isEmpty()) {
            res.status(400);
            return "Nom du joueur manquant";
        }
        playerService.addStarterPack(playerName);
        res.status(200);
        return "OK";
    });

    get("/setDay", (request, response) -> {
        mapService.changeToDay();
        response.status(200);
        return "OK";
    });
    }


    private void enableCORS(final String origin, final String methods, final String headers) {
        options("/*", (request, response) -> {
            String requestHeaders = request.headers("Access-Control-Request-Headers");
            if (requestHeaders != null) {
                response.header("Access-Control-Allow-Headers", requestHeaders);
            }

            String requestMethod = request.headers("Access-Control-Request-Method");
            if (requestMethod != null) {
                response.header("Access-Control-Allow-Methods", requestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            response.type("application/json");
        });
    }


}
