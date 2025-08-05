package org.julienLempereur.visualGame;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public final class VisualGame extends JavaPlugin implements Listener {

    InventaireWebSocket wsServer;

    public record PlayerInventory(
            String playerName,
            List<ItemSlot> items
    ) {}

    public record ItemSlot(
            String material,
            int amount
    ) {}

    @Override
    public void onEnable() {
        // Plugin startup logic
        try{
            getServer().getPluginManager().registerEvents(this, this);
            wsServer = new InventaireWebSocket(8887);
            wsServer.start();
            getLogger().info("✅ WebSocket démarré !");
        }
        catch (Exception e){
            getLogger().severe("/////////////////FAILED :" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

}




