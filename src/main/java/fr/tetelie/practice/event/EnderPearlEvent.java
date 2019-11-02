package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.match.MatchStatus;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderPearlEvent implements Listener {

    @EventHandler
    public void onin(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        PlayerManager pm = PlayerManager.getPlayerManagers().get(p.getUniqueId());
        ItemStack current = e.getItem();

        if (current == null) return;

        if (current.getType() == Material.ENDER_PEARL) {
            if(pm.getPlayerSatus() == PlayerSatus.FIGHT && MatchManager.getMatchManager(p.getUniqueId()) != null && MatchManager.getMatchManager(p.getUniqueId()).getMatchStatus() == MatchStatus.PLAYING) {

                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    if(pm.getEnderPearl() == 0) {
                        pm.setEnderPearl(15);
                        p.setLevel(15);
                        new BukkitRunnable() {
                            public void run() {
                                    pm.setEnderPearl(pm.getEnderPearl()-1);
                                    p.setLevel(pm.getEnderPearl());
                                    if(pm.getEnderPearl() == 0)
                                    {
                                        this.cancel();
                                    }
                            }
                        }.runTaskTimer((Plugin) Practice.getInstance(), 20L, 20L);

                    }else {
                        e.setCancelled(true);
                        p.updateInventory();
                        p.sendMessage("§6You can use enderpearl in §7" + pm.getEnderPearl() + " §6second(s)");
                    }
                }
            }else {
                e.setCancelled(true);
                p.updateInventory();
            }
        }

    }


}
