package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.setting.Setting;
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
        //e.setJoinMessage("§7[§6+§7] §e"+player.getName());
        e.setJoinMessage(null);
        PlayerManager playerManager = new PlayerManager(player.getUniqueId(), player.getName());
        playerManager.hideFromAll(player);
        playerManager.hideAll(player);
        Setting.all[0].change(player, playerManager.getSettings()[0]);
        playerManager.reset(player, GameMode.SURVIVAL);
        playerManager.teleport(player, Practice.getInstance().spawn);
        playerManager.sendKit(Practice.getInstance().spawnKit);
    }

}
