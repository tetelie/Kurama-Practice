package fr.tetelie.practice.quest.quests;

import fr.tetelie.practice.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExampleQuest extends Quest {

    @Override
    public String name() {
        return "Example Quest";
    }

    @Override
    public ItemStack iconItem() {
        return null;
    }

    @Override
    public void giveReward(Player player) {

    }

    public ExampleQuest() {
        super();
    }

    @Override
    public void finish(Player player) {
        super.finish(player);
    }
}
