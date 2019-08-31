package fr.tetelie.practice.event;

import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBlockEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e)
    {
        if(PlayerManager.getPlayerManagers().get(e.getPlayer().getUniqueId()).getPlayerSatus() != PlayerSatus.BUILD) e.setCancelled(true);
    }

}
