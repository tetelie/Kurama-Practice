package fr.tetelie.practice.event.custom;

import fr.tetelie.practice.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QuestCompleteEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private Quest quest;

    public QuestCompleteEvent(Player player, Quest quest) {
        this.player = player;
        this.quest = quest;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Quest getQuest() {
        return quest;
    }
}

