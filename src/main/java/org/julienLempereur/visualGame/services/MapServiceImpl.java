package org.julienLempereur.visualGame.services;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.julienLempereur.visualGame.VisualGame;
import org.julienLempereur.visualGame.model.MapModel;

public class MapServiceImpl implements MapService{
    @Override
    public MapModel sendMapUpdate() {
        World world = Bukkit.getWorlds().getFirst();
        return new MapModel(isDay(world));
    }

    @Override
    public Boolean isDay(World world) {
        return world.getTime()<12000;
    }

    @Override
    public void changeToDay() {
        Bukkit.getScheduler().runTask(VisualGame.getInstance(), () ->{
            Bukkit.getWorlds().getFirst().setTime(1000);
        });
    }
}
