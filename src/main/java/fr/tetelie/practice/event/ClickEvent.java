package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.match.MatchType;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        ItemStack current = e.getCurrentItem();
        if(current == null) return;
        Inventory inventory = e.getClickedInventory();
        if(current.hasItemMeta()) {
            Player player = (Player) e.getWhoClicked();
            PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
            if(playerManager.getPlayerSatus() == PlayerSatus.FREE) {
                e.setCancelled(true);
                if (inventory.getName().equals("§6Fight")) {
                    if (current.getType() == Material.FEATHER && current.getItemMeta().getDisplayName().equals("§eNormal")) {
                        player.openInventory(Practice.getInstance().normalFight);
                    } else if (current.getType() == Material.EXP_BOTTLE && current.getItemMeta().getDisplayName().equals("§6Competitive")) {
                        player.openInventory(Practice.getInstance().competitiveFight);
                    }
                } else if(inventory.getName().equals("§eNormal Fight"))
                {
                    if(Practice.getInstance().fight.containsKey(current.getItemMeta().getDisplayName()))
                    {
                        player.closeInventory();
                        FightManager fightManager = Practice.getInstance().fight.get(current.getItemMeta().getDisplayName());
                        if(fightManager.getQueue(FightType.NORMAL) == 1)
                        {
                            // TODO start match
                            player.sendMessage("An opponent was found!");
                            new MatchManager(MatchType.DUEL, fightManager.getQueuePlayer().get(FightType.NORMAL), player.getUniqueId(), FightType.NORMAL, current.getItemMeta().getDisplayName());
                        }else{
                            playerManager.queue(current.getItemMeta().getDisplayName(), FightType.NORMAL);
                            playerManager.setPlayerSatus(PlayerSatus.QUEUE);
                            playerManager.sendKit(Practice.getInstance().queueKit);
                            player.sendMessage("You are added to the queue");
                        }
                    }
                }
            }
        }
    }

}
