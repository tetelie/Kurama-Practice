package fr.tetelie.practice.event;

import fr.tetelie.practice.match.MatchStatus;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            PlayerManager playerManager = PlayerManager.getPlayerManagers().get(e.getEntity().getUniqueId());
            if(playerManager.getPlayerSatus()  == PlayerSatus.FIGHT)
            {
                if(playerManager.getCurrentFight() != null && playerManager.getCurrentFight().getMatchStatus() != MatchStatus.PLAYING)
                {
                    e.setCancelled(true);
                }
            }else{
                e.setCancelled(true);
            }
        }
    }

}
