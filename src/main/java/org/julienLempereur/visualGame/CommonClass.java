package org.julienLempereur.visualGame;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.julienLempereur.visualGame.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class CommonClass {
    @Getter
    private static final CommonClass instance = new CommonClass();

    private String uuid;

    public List<Map<String, String>> playersStock = new ArrayList<>();

    private CommonClass(){};


    public Boolean verifCode(String uuid){
       return playersStock.stream().anyMatch(map ->
                map.containsKey(uuid));
    }
}
