package org.julienLempereur.visualGame;
import static spark.Spark.*;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SparkService {

    private final Gson gson = new Gson();
    public SparkService(){
    port(4567);

    enableCORS("*","*","*");

    get("/hello", (request, response) -> "Hello world");

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
