package org.julienLempereur.visualGame.services;

import org.bukkit.entity.Player;
import org.julienLempereur.visualGame.VisualGame;
import org.julienLempereur.visualGame.model.PlayerModel;

import java.util.List;

public interface PlayerService {
    List<PlayerModel> sendInventaireUpdate();

    void addStarterPack(String playerName) throws Exception;

    PlayerModel dtoPlayerToPlayerModel(Player player);
}
