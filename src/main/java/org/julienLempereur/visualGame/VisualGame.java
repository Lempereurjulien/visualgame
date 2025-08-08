package org.julienLempereur.visualGame;
import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent;
import com.google.gson.GsonBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public final class VisualGame extends JavaPlugin implements Listener {

    InventaireWebSocket wsServer;
    private SparkService sparkService;

    public record PlayerInventory(
            String playerName,
            List<ItemSlot> items
    ) {}

    public record ItemSlot(
            String material,
            int amount
    ) {}

    private ScheduledExecutorService scheduler;

    @Override
    public void onEnable() {
        sparkService = new SparkService(this);
        String uuid = UUID.randomUUID().toString().substring(0,4).toUpperCase();
        CommonClass.getInstance().setUuid(uuid);
        // Plugin startup logic
        try{
            getServer().getPluginManager().registerEvents(this, this);
            wsServer = new InventaireWebSocket(8887);
            wsServer.start();
            getLogger().info("✅ WebSocket démarré !");
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> sendInventaireUpdate(),0,1, TimeUnit.SECONDS);

        }
        catch (Exception e){
            getLogger().severe("/////////////////FAILED :" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
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

    public void sendInventaireUpdate(){

        List<PlayerInventory> playerInventories = new ArrayList<>();

        for(Player player : Bukkit.getOnlinePlayers()){
            List<ItemSlot> itemSlots = new ArrayList<>();
            for(ItemStack itemStack : player.getInventory().getContents()){
                if(itemStack != null){
                    ItemSlot itemSlot = new ItemSlot(itemStack.getType().toString(), itemStack.getAmount());
                    itemSlots.add(itemSlot);
                }
            }
            PlayerInventory playerInventory = new PlayerInventory(player.getName(),itemSlots);
            playerInventories.add(playerInventory);
        }
        String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(playerInventories);
        if(wsServer !=null){
            wsServer.broadcastMessage(json);
        }
        else{
            getLogger().warning("! wsServer est null, brodcast ignoré");
        }
    }

    //EVENT Handler
    @EventHandler
    public void pickupItemEvent(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player player){
            sendInventaireUpdate();
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e){
        sendInventaireUpdate();
    }

    @EventHandler
    public void craftItemEvent(CraftItemEvent e){
        sendInventaireUpdate();
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e){
        sendInventaireUpdate();
    }

    @EventHandler
    public void playerConnect(PlayerJoinEvent e){
        e.joinMessage(Component.text("code : " + CommonClass.getInstance().getUuid()).color(NamedTextColor.GREEN));
    }


}




