package fr.tetelie.practice.quest.quests;

import fr.tetelie.practice.quest.Quest;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class ExampleQuest extends Quest {

    @Override
    public String name() {
        return "Quète exemple";
    }

    @Override
    public ItemStack iconItem() {
        return new ItemBuilder(Material.NAME_TAG).setName("§a§lQuètes exemple").setLore(Quest.getQuestLore("Send a message in the chat", "nothing lol")).toItemStack();
    }

    @Override
    public void giveReward(Player player) {
        player.getInventory().addItem(new ItemStack(Material.BREAD));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        giveReward(e.getPlayer());
    }

}
