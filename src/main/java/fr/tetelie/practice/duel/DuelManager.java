package fr.tetelie.practice.duel;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.match.MatchType;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class DuelManager {

    static @Getter List<DuelManager> all;

    static
    {
        all = new ArrayList<>();
    }

    private UUID sender;
    private UUID reciever;
    private String ladderDisplayName;

    public DuelManager(UUID sender, UUID reciever, String ladderDisplayName)
    {
        this.sender = sender;
        this.reciever = reciever;
        this.ladderDisplayName = ladderDisplayName;
        setup();

        all.add(this);
    }

    private void setup()
    {
        Player senderP = Bukkit.getPlayer(sender);
        Player recieverP = Bukkit.getPlayer(reciever);
        senderP.sendMessage("§6Your duel request is successfully sent!");
        senderP.sendMessage("§eYour duel will expire in 15 seconds!");
        TextComponent duel = new TextComponent("§e"+senderP.getName() + " §6has send to you a duel request in " + ladderDisplayName + " ");
        TextComponent click = new TextComponent("§7(Click to accept)");
        click.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept versus_duel " + senderP.getUniqueId()));
        click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click here to create the match!").create()));
        duel.addExtra(click);
        recieverP.spigot().sendMessage(duel);

        Bukkit.getScheduler().runTaskLater(Practice.getInstance(), (Runnable) new Runnable() {
            public void run() {
                if(Bukkit.getPlayer(sender) != null && PlayerManager.getPlayerManagers().get(sender).getPlayerSatus() == PlayerSatus.FREE)
                {
                    senderP.sendMessage("§cYour duel has expired!");
                }
                destroy();
            }
        }, 20*15);
    }

    public void create()
    {
        new MatchManager(MatchType.DUEL, sender, reciever, FightType.NORMAL, ladderDisplayName);
    }

    public void destroy()
    {
        all.remove(this);
    }

    public static DuelManager getDuelBySender(UUID sender)
    {
        return all.stream().filter(duelManager -> duelManager.getSender().equals(sender)).findFirst().orElse(null);
    }

    public static DuelManager getDuelByReciever(UUID reciever)
    {
        return all.stream().filter(duelManager -> duelManager.getReciever().equals(reciever)).findFirst().orElse(null);
    }

}
