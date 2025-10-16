package org.julienLempereur.visualGame.services;

import org.bukkit.entity.Player;
import org.julienLempereur.visualGame.model.ItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemServiceImpl implements ItemService{
    @Override
    public List<ItemModel> getAllItem(Player player) {
        List<ItemModel> list = new ArrayList<>();
        Arrays.stream(player.getInventory().getContents()).forEach(
                itemStack -> {
                    ItemModel item = new ItemModel(itemStack.getType().toString(), itemStack.getAmount());
                    list.add(item);
                }
        );
        return list;
    }
}
