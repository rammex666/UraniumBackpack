package fr.rammex.uraniumbackpack.version.core.events;

import fr.rammex.uraniumbackpack.database.sqlite.InventoryDAO;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class UBackPackPlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();

        UUID uuid = player.getUniqueId();
        if(!InventoryDAO.isPlayerRegistered(uuid)) {
            return;
        }
        ItemStack[] items = InventoryDAO.loadInventory(uuid);
        player.getInventory().setContents(items);
        player.sendMessage("Your inventory has been restored");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ItemStack[] items = player.getInventory().getContents();
        if(items != null) {
            InventoryDAO.saveInventory(player);
            System.out.println("Inventory saved");
        }
    }
}
