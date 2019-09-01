package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        e.setJoinMessage("§7[§6+§7] §e"+player.getName());
        PlayerManager playerManager = new PlayerManager(player.getUniqueId(), player.getName());
        playerManager.reset(player, GameMode.SURVIVAL);
        playerManager.teleport(player, Practice.getInstance().spawn);
        playerManager.sendKit(Practice.getInstance().spawnKit);
    }

}
