package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        ItemStack current = e.getItem();
        if(current == null) return;
        Action action = e.getAction();
        if(current.hasItemMeta()) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                Player player = e.getPlayer();
                PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
                if(playerManager.getPlayerSatus() == PlayerSatus.FREE)
                {
                    e.setCancelled(true);
                    if(current.getType() == Material.WOOD_SWORD && current.getItemMeta().getDisplayName().equals("§6§lFight §r§f(Right click)"))
                    {
                        player.openInventory(Practice.getInstance().fightGui.inventory());
                    }else if(current.getType() == Material.CAULDRON_ITEM && current.getItemMeta().getDisplayName().equals("§6§lHistoric §r§f(Right click)"))
                    {
                        player.openInventory(playerManager.getHistoricManager().getHistoricInventory(player, 1));
                    }
                }
            }
        }
    }

}
