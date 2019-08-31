package fr.tetelie.practice.event;

import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodEvent implements Listener {

    @EventHandler
    public void onFood(FoodLevelChangeEvent e)
    {
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(e.getEntity().getUniqueId());
        if(playerManager.getPlayerSatus() != PlayerSatus.FIGHT) e.setCancelled(true);
    }

}
