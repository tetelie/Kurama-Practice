package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
                        playerManager.getHistoric().open(player);
                    }else if(current.getType() == Material.PAINTING && current.getItemMeta().getDisplayName().equals("§6§lPanel §r§f(Right click)"))
                    {
                        player.openInventory(Practice.getInstance().panelGui.inventory());
                    }else if(current.getType() == Material.FIRE && current.getItemMeta().getDisplayName().equals("§6§lRematch §r§f(Right click)"))
                    {
                        if(playerManager.getCurrentDuelPlayer() != null)
                        {
                            if(Bukkit.getPlayer(playerManager.getCurrentDuelPlayer()) != null) {
                                Bukkit.dispatchCommand(player, "duel " + Bukkit.getPlayer(playerManager.getCurrentDuelPlayer()).getName());
                            }else{
                                playerManager.sendKit(Practice.getInstance().spawnKit);
                                player.sendMessage("§cThis player is offline!");
                            }
                        }
                    }else if(current.getType() == Material.COMPASS && current.getItemMeta().getDisplayName().equals("§6§lSpectate §r§f(Right click)"))
                    {
                        List<ItemStack> matchs = new ArrayList<>();
                        for (MatchManager matchManager : MatchManager.getAll()) {
                            ItemBuilder item = matchManager.getFightType() == FightType.NORMAL ? new ItemBuilder(Material.FEATHER) : new ItemBuilder(Material.EXP_BOTTLE);
                            item.setName("§6"+ matchManager.getName1() + " §evs §6" + matchManager.getName2());
                            List<String> lore = new ArrayList<>();
                            lore.add(" ");
                            lore.add("§6Type§7: §e"+matchManager.getFightType().toString().toLowerCase());
                            lore.add("§6Ladder§7: §e"+matchManager.getLadder().name());
                            lore.add("§6Arena§7: §e"+matchManager.getArena().getName());
                            lore.add("§6Status§7: §e"+matchManager.getMatchStatus().toString().toLowerCase());
                            item.setLore(lore);
                            matchs.add(item.toItemStack());
                        }
                        Practice.getInstance().spectateGui.setItemStacks(matchs);
                        Practice.getInstance().spectateGui.open(player, 1);
                    }else if(current.getType() == Material.NETHER_STAR && current.getItemMeta().getDisplayName().equals("§6§lEvent §r§f(Right click)"))
                    {
                        Practice.getInstance().eventGui.open(player, 1);
                    }
                }else if(playerManager.getPlayerSatus() == PlayerSatus.QUEUE)
                {
                    if(current.getType() == Material.INK_SACK && current.getItemMeta().getDisplayName().equals("§6Leave the queue."))
                    {
                        playerManager.leaveQueue();
                        playerManager.setPlayerSatus(PlayerSatus.FREE);
                        playerManager.sendKit(Practice.getInstance().spawnKit);
                    }
                } else if(playerManager.getPlayerSatus() == PlayerSatus.PARTY)
                {
                    if(current.getType() == Material.FIRE && current.getItemMeta().getDisplayName().equals("§6§lDisband party §r§f(Right click)"))
                    {
                        Practice.getInstance().party.get(player.getUniqueId()).destroy();
                        playerManager.sendKit(Practice.getInstance().spawnKit);
                        playerManager.setPlayerSatus(PlayerSatus.FREE);
                    }
                }
            }
        }
    }

}
