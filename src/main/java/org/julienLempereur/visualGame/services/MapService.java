package org.julienLempereur.visualGame.services;

import org.bukkit.World;
import org.julienLempereur.visualGame.model.MapModel;

public interface MapService {

    public MapModel sendMapUpdate();

    public Boolean isDay(World world);

    public void changeToDay();
}
