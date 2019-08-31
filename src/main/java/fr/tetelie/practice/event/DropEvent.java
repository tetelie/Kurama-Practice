package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e)
    {
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(e.getPlayer().getUniqueId());
        if(playerManager.getPlayerSatus() == PlayerSatus.FIGHT)
        {
            if(e.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD
                    || e.getItemDrop().getItemStack().getType() == Material.IRON_AXE
                    || e.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD
                    || e.getItemDrop().getItemStack().getType() == Material.STONE_SWORD){ e.setCancelled(true);
                    return; }
            Bukkit.getScheduler().runTaskLater(Practice.getInstance(), (Runnable) new Runnable() {
                public void run() {
                    e.getItemDrop().remove();
                }
            }, 100);
        }else{
            e.setCancelled(true);
        }
    }

}
