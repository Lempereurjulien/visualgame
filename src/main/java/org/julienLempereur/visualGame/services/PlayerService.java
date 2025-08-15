package org.julienLempereur.visualGame.services;


import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.julienLempereur.visualGame.websocket.InventaireWebSocket;
import org.julienLempereur.visualGame.allClass.ItemClass;
import org.julienLempereur.visualGame.allClass.PlayerClass;
import org.julienLempereur.visualGame.websocket.WebSocketManager;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

@AllArgsConstructor
public class PlayerService {


    public List<PlayerClass> sendInventaireUpdate(){

        List<PlayerClass> allPlayers = new ArrayList<>();

        for(Player player : Bukkit.getOnlinePlayers()){
            List<ItemClass> itemSlots = new ArrayList<>();
            for(ItemStack itemStack : player.getInventory().getContents()){
                if(itemStack != null){
                    ItemClass itemSlot = new ItemClass(itemStack.getType().toString(), itemStack.getAmount());
                    itemSlots.add(itemSlot);
                }
            }
            PlayerClass playerInventory = new PlayerClass(player.getName(), true, new ArrayList<>());
            playerInventory.addItemSlots(itemSlots);
            allPlayers.add(playerInventory);
        }
        String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(allPlayers);
        if(WebSocketManager.getInstance() !=null){
            WebSocketManager.getInstance().broadcastMessage(json);
        }
        else{
            getLogger().warning("! wsServer est null, brodcast ignoré");
        }
        return allPlayers;
    }
}
