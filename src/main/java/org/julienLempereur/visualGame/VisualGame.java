package org.julienLempereur.visualGame;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.julienLempereur.visualGame.model.PlayerModel;
import org.julienLempereur.visualGame.services.MapService;
import org.julienLempereur.visualGame.services.MapServiceImpl;
import org.julienLempereur.visualGame.services.PlayerService;
import org.julienLempereur.visualGame.services.PlayerServiceImpl;
import org.julienLempereur.visualGame.websocket.WebSocketManager;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class VisualGame extends JavaPlugin implements Listener {

    //    private PlayerServiceImpl playerService;
    private SparkService sparkService;
    private ScheduledExecutorService scheduler;
    @Getter
    public static VisualGame instance;
    private PlayerService playerService = new PlayerServiceImpl();




    @Override
    public void onEnable() {
        instance = this;
        sparkService = new SparkService();
        getServer().getPluginManager().registerEvents(this, this);
        try {
            WebSocketManager.start(8887);
            MapService mapService = new MapServiceImpl();

            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    WebSocketManager.getInstance().broadCastAllPlayers(playerService.sendInventaireUpdate());
                    WebSocketManager.getInstance().broadCastMap(mapService.sendMapUpdate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1000, 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            getLogger().severe("/////////////////FAILED :" + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        WebSocketManager.stop();
        // Plugin shutdown logic
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        spark.Spark.stop();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @EventHandler
    public void playerConnect(PlayerJoinEvent e) {
        Map<String, String> map = new HashMap<>();
        String code = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        PlayerModel player = playerService.dtoPlayerToPlayerModel(e.getPlayer());
        e.getPlayer().sendMessage(Component.text("code : " + code).color(NamedTextColor.GREEN));
        map.put(code, player.getName());
        List<Map<String, String>> playersStock = CommonClass.getInstance().getPlayersStock();
        playersStock.add(map);
    }

    @EventHandler
    public void playerDie(PlayerDeathEvent e) {
    }


}




