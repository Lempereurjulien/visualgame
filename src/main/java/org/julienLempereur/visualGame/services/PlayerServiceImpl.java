package org.julienLempereur.visualGame.services;


import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.julienLempereur.visualGame.model.ItemModel;
import org.julienLempereur.visualGame.model.PlayerModel;
import org.julienLempereur.visualGame.websocket.WebSocketManager;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    static ItemService itemService = new ItemServiceImpl();

    public void sendInventaireUpdate() {
        /// Recupérer tous les joueurs existant et les envoyer en format Json
        List<ItemModel> itemSlots = new ArrayList<>();
        List<PlayerModel> allPlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream().forEach(
                player -> {
                    allPlayers.add(dtoPlayer(player));
                });
        String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(allPlayers);
        if (WebSocketManager.getInstance() != null) {
            WebSocketManager.getInstance().broadcastMessage(json);
        } else {
            getLogger().warning("! wsServer est null, brodcast ignoré");
        }
    }


    public static PlayerModel dtoPlayer(Player player) {
        List<ItemModel> allItem = itemService.getAllItem(player);
        return new PlayerModel(player.getName(), !player.isDead(), allItem);
    }

}
