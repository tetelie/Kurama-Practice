package fr.tetelie.practice.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e)
    {
        e.setRespawnLocation(e.getPlayer().getLocation());
    }

}
