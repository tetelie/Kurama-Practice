package fr.tetelie.practice.event;

import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityEvent implements Listener {

    @EventHandler
    public void onAttck(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Player)
        {
            Player player = (Player)e.getDamager();
            PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
            if(playerManager.getPlayerSatus() != PlayerSatus.FIGHT)
            {
                e.setCancelled(true);
            }
        }
    }

}
