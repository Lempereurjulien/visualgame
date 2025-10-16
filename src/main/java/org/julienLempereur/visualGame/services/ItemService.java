package org.julienLempereur.visualGame.services;

import org.bukkit.entity.Player;
import org.julienLempereur.visualGame.model.ItemModel;

import java.util.List;

public interface ItemService {

    List<ItemModel> getAllItem(Player player);
}
