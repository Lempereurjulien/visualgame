package org.julienLempereur.visualGame;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.julienLempereur.visualGame.model.PlayerModel;
import org.julienLempereur.visualGame.services.PlayerService;
import org.julienLempereur.visualGame.services.PlayerServiceImpl;
import org.julienLempereur.visualGame.websocket.WebSocketManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public final class VisualGame extends JavaPlugin implements Listener {

//    private PlayerServiceImpl playerService;
    private SparkService sparkService;
    private ScheduledExecutorService scheduler;

    @Override
    public void onEnable() {
        sparkService = new SparkService(this);
        String uuid = UUID.randomUUID().toString().substring(0,4).toUpperCase();
        CommonClass.getInstance().setUuid(uuid);
        getServer().getPluginManager().registerEvents(this, this);
        try{
            WebSocketManager.start(8887);
        PlayerService playerService = new PlayerServiceImpl();
        scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    WebSocketManager.getInstance().broadCastMessage("teste");
                    List<PlayerModel> players = playerService.sendInventaireUpdate();
                    WebSocketManager.getInstance().broadCastAllPlayers(players);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1000, 1000, TimeUnit.MILLISECONDS);
        }
        catch (Exception e){
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
    public void playerConnect(PlayerJoinEvent e){
        e.joinMessage(Component.text("code : " + CommonClass.getInstance().getUuid()).color(NamedTextColor.GREEN));
    }

    @EventHandler
    public void playerDie(PlayerDeathEvent e){
    }


}




