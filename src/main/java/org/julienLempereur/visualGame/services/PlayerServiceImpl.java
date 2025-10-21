package org.julienLempereur.visualGame.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.julienLempereur.visualGame.VisualGame;
import org.julienLempereur.visualGame.model.ItemModel;
import org.julienLempereur.visualGame.model.PlayerModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerServiceImpl implements PlayerService{
    @Override
    public List<PlayerModel> sendInventaireUpdate() {
        List<PlayerModel> playerModels = new ArrayList<>();
        Bukkit.getOnlinePlayers()
                .forEach(player -> {
                    playerModels.add(new PlayerModel(player.getName(), !player.isDead(),getItemByPlayer(player)));
                });
        return playerModels;
    }

    @Override
    public void addStarterPack(VisualGame plugin, String playerName) {

    }

    private List<ItemModel> getItemByPlayer(Player player){
        List<ItemModel> listItem = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(player.getInventory().getContents()))
                .filter(Objects::nonNull)
                .forEach(itemStack -> {
                    listItem.add(new ItemModel(
                            itemStack.getType().toString(),
                            itemStack.getAmount()));
                });
        return listItem;
    }
}
