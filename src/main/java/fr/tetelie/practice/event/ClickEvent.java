package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.duel.DuelManager;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.match.MatchType;
import fr.tetelie.practice.party.PartyManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import fr.tetelie.practice.quest.Quest;
import fr.tetelie.practice.setting.Setting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (current == null) return;
        Inventory inventory = e.getClickedInventory();
        Player player = (Player) e.getWhoClicked();
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
        if (inventory.getName().equals("§6Spectate") && (playerManager.getPlayerSatus() == PlayerSatus.SPECTATE || playerManager.getPlayerSatus() == PlayerSatus.FREE)) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW && current.getItemMeta().getDisplayName().equals("§6Next page") && current.getItemMeta().hasLore()) {
                String str = current.getItemMeta().getLore().get(0).substring(7);
                Practice.getInstance().spectateGui.open(player, Integer.parseInt(str));
            } else if (current.getType() == Material.LEVER && current.getItemMeta().getDisplayName().equals("§6Previous page") && current.getItemMeta().hasLore()) {
                String str = current.getItemMeta().getLore().get(0).substring(7);
                Practice.getInstance().spectateGui.open(player, Integer.parseInt(str));
            } else if (current.getType() == Material.LEVER && current.getItemMeta().getDisplayName().equals("§6Previous page") && current.getItemMeta().hasLore()) {
                String str = current.getItemMeta().getLore().get(0).substring(7);
                Practice.getInstance().spectateGui.open(player, Integer.parseInt(str));
            } else if (current.getType() == Material.FEATHER) {
                String title = current.getItemMeta().getDisplayName();
                String arr[] = title.split(" ", 2);
                String first = arr[0];
                ChatColor.stripColor(first);
                Bukkit.dispatchCommand(player, "spec " + ChatColor.stripColor(first));
            }
        } else if (playerManager.getPlayerSatus() == PlayerSatus.FREE) {
            e.setCancelled(true);
            if (current.hasItemMeta()) {
                if (inventory.getName().equals("§6Fight")) {
                    if (current.getType() == Material.FEATHER && current.getItemMeta().getDisplayName().equals("§eNormal")) {
                        player.openInventory(Practice.getInstance().normalFight);
                    } else if (current.getType() == Material.EXP_BOTTLE && current.getItemMeta().getDisplayName().equals("§6Competitive")) {
                        player.openInventory(Practice.getInstance().competitiveFight);
                    }
                } else if (inventory.getName().equals("§eNormal Fight")) {
                    if (Practice.getInstance().fight.containsKey(current.getItemMeta().getDisplayName())) {
                        player.closeInventory();
                        FightManager fightManager = Practice.getInstance().fight.get(current.getItemMeta().getDisplayName());
                        player.sendMessage("§dYou are now queued for §5UnRanked " + ChatColor.stripColor(current.getItemMeta().getDisplayName()));
                        if (fightManager.getQueue(FightType.NORMAL) == 1) {
                            new MatchManager(MatchType.DUEL, fightManager.getNormalQueuePlayer(), player.getUniqueId(), FightType.NORMAL, current.getItemMeta().getDisplayName());
                        } else {
                            playerManager.queue(current.getItemMeta().getDisplayName(), FightType.NORMAL);
                            playerManager.setPlayerSatus(PlayerSatus.QUEUE);
                            playerManager.sendKit(Practice.getInstance().queueKit);
                        }
                    }
                } else if (inventory.getName().equals("§6Competitive Fight")) {
                    player.closeInventory();
                    playerManager.queue(current.getItemMeta().getDisplayName(), FightType.COMPETITIVE);
                    playerManager.setPlayerSatus(PlayerSatus.QUEUE);
                    playerManager.sendKit(Practice.getInstance().queueKit);
                    player.sendMessage("§dYou are now queued for §5Ranked " + ChatColor.stripColor(current.getItemMeta().getDisplayName()));
                    playerManager.getRanked().start(playerManager, player);

                } else if (inventory.getName().equals("§6Duel")) {
                    if (playerManager.getCurrentDuelPlayer() != null && Bukkit.getPlayer(playerManager.getCurrentDuelPlayer()) != null) {
                        player.closeInventory();
                        new DuelManager(player.getUniqueId(), playerManager.getCurrentDuelPlayer(), current.getItemMeta().getDisplayName());
                    } else {
                        player.sendMessage("§cThe target player is not connected!");
                    }
                } else if (inventory.getName().equals("§6Panel")) {
                    if (current.getType() == Material.REDSTONE_COMPARATOR && current.getItemMeta().getDisplayName().equals("§eSettings")) {
                        player.openInventory(playerManager.getSettingsGui());
                    } else if (current.getType() == Material.BREWING_STAND_ITEM && current.getItemMeta().getDisplayName().equals("§eStatistics")) {
                        player.openInventory(playerManager.getStatsGui());
                    }else if (current.getType() == Material.EMERALD && current.getItemMeta().getDisplayName().equals("§eLeaderboard")) {
                        player.openInventory(Practice.getInstance().leaderboardInventory);
                    }
                } else if (inventory.getName().equals("§6§lSettings")) {
                    int setting = Setting.getSettingsBySlot(e.getSlot());
                    playerManager.changeSettings(setting, player);
                    playerManager.refreshSettingLore(inventory, e.getSlot(), setting);
                }
            }
        } else if (playerManager.getPlayerSatus() == PlayerSatus.QUEUE) {
            e.setCancelled(true);
        } else if (playerManager.getPlayerSatus() == PlayerSatus.SPECTATE) {
            e.setCancelled(true);
        }
    }

}
