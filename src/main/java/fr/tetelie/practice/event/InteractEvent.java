package fr.tetelie.practice.event;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.fightpass.FightPass;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.party.PartyManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
                    }else if(current.getType() == Material.PAPER && current.getItemMeta().getDisplayName().equals("§6§lFight Pass §r§f(Right click)"))
                    {
                        //playerManager.getHistoric().open(player);
                        FightPass.open(player, 0);
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
                            lore.add("§6Duration§7: §e"+ matchManager.getDuration());
                            item.setLore(lore);
                            matchs.add(item.toItemStack());
                        }
                        Practice.getInstance().spectateGui.refresh(matchs);
                        Practice.getInstance().spectateGui.open(player, 1);
                    }else if(current.getType() == Material.NAME_TAG && current.getItemMeta().getDisplayName().equals("§6§lCreate party §r§f(Right click)"))
                    {
                        //Practice.getInstance().eventGui.open(player, 1);
                        player.closeInventory();
                        new PartyManager(playerManager, player);
                    }else if(current.getType() == Material.BOOK && current.getItemMeta().getDisplayName().equals("§6§lEdit kit §r§f(Right click)"))
                    {
                        //Practice.getInstance().eventGui.open(player, 1);
                        player.openInventory(Practice.getInstance().editorGui.inventory());
                    }
                }else if(playerManager.getPlayerSatus() == PlayerSatus.QUEUE)
                {
                    if(current.getType() == Material.INK_SACK && current.getItemMeta().getDisplayName().equals("§6Leave the queue."))
                    {
                        String type = playerManager.getFightType() == FightType.NORMAL ? "Unranked" : "Ranked";
                        player.sendMessage("§dYou are no longer queued for §5"+type+" "+ ChatColor.stripColor(playerManager.getLadder()));
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
                } else if(playerManager.getPlayerSatus() == PlayerSatus.SPECTATE)
                {
                    if(current.getType() == Material.INK_SACK && current.getItemMeta().getDisplayName().equals("§6Back to spawn"))
                    {
                        playerManager.unSpectate(player, true);
                        playerManager.reset(player, GameMode.SURVIVAL);
                        playerManager.setPlayerSatus(PlayerSatus.FREE);
                        playerManager.sendKit(Practice.getInstance().spawnKit);
                        playerManager.teleport(player, Practice.getInstance().spawn);
                    }
                }
            }
        }
    }

}
