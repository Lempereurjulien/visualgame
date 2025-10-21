package org.julienLempereur.visualGame.services;

import org.bukkit.entity.Player;
import org.julienLempereur.visualGame.VisualGame;

public interface PlayerService {
    void sendInventaireUpdate();

    void addStarterPack(VisualGame plugin, String playerName);

}
