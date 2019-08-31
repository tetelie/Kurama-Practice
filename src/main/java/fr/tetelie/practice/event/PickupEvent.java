package fr.tetelie.practice.event;

import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupEvent implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e)
    {
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(e.getPlayer().getUniqueId());
        if(playerManager.getPlayerSatus() != PlayerSatus.FIGHT)
        {
            e.setCancelled(true);
        }
    }

}
